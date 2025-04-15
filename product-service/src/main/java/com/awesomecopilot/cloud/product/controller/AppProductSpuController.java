package com.awesomecopilot.cloud.product.controller;

import com.awesomecopilot.cloud.product.entity.ProductSku;
import com.awesomecopilot.cloud.product.entity.ProductSpu;
import com.awesomecopilot.cloud.product.enums.ProductSpuStatusEnum;
import com.awesomecopilot.cloud.product.service.ProductBrowseHistoryService;
import com.awesomecopilot.cloud.product.service.ProductSkuService;
import com.awesomecopilot.cloud.product.service.ProductSpuService;
import com.awesomecopilot.cloud.product.vo.AppProductSpuDetailRespVO;
import com.awesomecopilot.cloud.product.vo.AppProductSpuPageReqVO;
import com.awesomecopilot.cloud.product.vo.AppProductSpuRespVO;
import com.awesomecopilot.cloud.product.vo.ProductSpuVO;
import com.awesomecopilot.common.lang.exception.BusinessException;
import com.awesomecopilot.common.lang.utils.CollectionUtils;
import com.awesomecopilot.common.lang.utils.StringUtils;
import com.awesomecopilot.common.lang.vo.Result;
import com.awesomecopilot.common.lang.vo.Results;
import com.awesomecopilot.common.spring.utils.BeanUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.*;

@Tag(name = "用户 APP - 商品 SPU")
@RestController
@RequestMapping("/product/spu")
@Validated
public class AppProductSpuController {

	@Autowired
	private ProductSpuService productSpuService;

	@Autowired
	private ProductSkuService productSkuService;

	@Autowired
	private ProductBrowseHistoryService productBrowseHistoryService;


	@GetMapping("/page")
	@Operation(summary = "获得商品 SPU 分页")
	@PermitAll
	public Result<List<AppProductSpuRespVO>> getSpuPage(@Valid AppProductSpuPageReqVO pageVO) {
		List<ProductSpuVO> pageResult = productSpuService.getSpuPage(pageVO);
		if (CollectionUtils.isEmpty(pageResult)) {
			return Results.<List<AppProductSpuRespVO>>success().build();
		}

		// 拼接返回
		pageResult.forEach(spu -> spu.setSalesCount(spu.getSalesCount() + spu.getVirtualSalesCount()));
		List<AppProductSpuRespVO> results = pageResult.stream().map((spu) -> {
			AppProductSpuRespVO appProductSpuRespVO = BeanUtils.copyProperties(spu, AppProductSpuRespVO.class);
			List<String> sliderPicUrls = appProductSpuRespVO.getSliderPicUrls();
			for (int i = 0; i < sliderPicUrls.size(); i++) {
			    String sliderPicUrl = sliderPicUrls.get(i);
				sliderPicUrl = StringUtils.cleanQuotationMark(sliderPicUrl);
				sliderPicUrls.set(i, sliderPicUrl);
			}
			return appProductSpuRespVO;
		}).collect(toList());
		Result<List<AppProductSpuRespVO>> result = Results.<List<AppProductSpuRespVO>>success()
				.page(pageVO.getPage())
				.data(results)
				.build();
		return result;
	}

	@GetMapping("/get-detail")
	@Operation(summary = "获得商品 SPU 明细")
	@Parameter(name = "id", description = "编号", required = true)
	@PermitAll
	public Result<AppProductSpuDetailRespVO> getSpuDetail(@RequestParam("id") Long id) {
		// 获得商品 SPU
		ProductSpu spu = productSpuService.getSpu(id);

		if (!ProductSpuStatusEnum.isEnable(spu.getStatus())) {
			throw new BusinessException("4298", "商品 SPU【"+spu.getName()+"】不处于上架状态");
		}

		// 获得商品 SKU
		List<ProductSku> skus = productSkuService.getSkuListBySpuId(id);
		// 增加浏览量
		productSpuService.updateBrowseCount(id);
		// 保存浏览记录
		productBrowseHistoryService.createBrowseHistory(295L, id);

		// 拼接返回
		spu.setSalesCount(spu.getSalesCount() + spu.getVirtualSalesCount());
		AppProductSpuDetailRespVO appProductSpuDetailRespVO =
				BeanUtils.copyProperties(spu, AppProductSpuDetailRespVO.class);
		List<AppProductSpuDetailRespVO.Sku> skus1 = BeanUtils.copyProperties(skus, AppProductSpuDetailRespVO.Sku.class);
		appProductSpuDetailRespVO.setSkus(skus1);

		Result<AppProductSpuDetailRespVO> result =
				Results.<AppProductSpuDetailRespVO>success().data(appProductSpuDetailRespVO).build();
		return result;
	}
}
