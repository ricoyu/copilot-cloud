package com.awesomecopilot.cloud.product.controller;


import com.awesomecopilot.cloud.product.api.dto.ProductSkuRespDTO;
import com.awesomecopilot.cloud.product.api.sku.ProductSkuApi;
import com.awesomecopilot.cloud.product.entity.ProductSku;
import com.awesomecopilot.cloud.product.service.ProductSkuService;
import com.awesomecopilot.common.lang.exception.BusinessException;
import com.awesomecopilot.common.lang.vo.Result;
import com.awesomecopilot.common.lang.vo.Results;
import com.awesomecopilot.common.spring.utils.BeanUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "用户 App - 商品 SKU")
@Slf4j
@Validated
@RestController
@RequestMapping("/product/sku")
public class AppProductSkuController implements ProductSkuApi {

	@Autowired
	private ProductSkuService productSkuService;

	@GetMapping("/get")
	@Operation(summary = "查询 SKU 信息")
	@Parameter(name = "id", description = "SKU 编号", required = true, example = "1024")
	public Result<ProductSkuRespDTO> getSku(@RequestParam("id") Long id) {
		ProductSku sku = productSkuService.getSku(id);
		if (sku == null) {
			throw new BusinessException("1001", "商品 SKU 不存在");
		}
		ProductSkuRespDTO productSkuRespDTO = BeanUtils.copyProperties(sku, ProductSkuRespDTO.class);
		Result<ProductSkuRespDTO> result = Results.<ProductSkuRespDTO>success().data(productSkuRespDTO).build();
		return result;
	}
}
