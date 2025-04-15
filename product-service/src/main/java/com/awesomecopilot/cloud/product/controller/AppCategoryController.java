package com.awesomecopilot.cloud.product.controller;

import com.awesomecopilot.cloud.product.service.category.ProductCategoryService;
import com.awesomecopilot.cloud.product.vo.AppCategoryRespVO;
import com.awesomecopilot.common.lang.vo.Result;
import com.awesomecopilot.common.lang.vo.Results;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "用户 APP - 商品分类")
@RestController
@RequestMapping("/product/category")
@Validated
public class AppCategoryController {

	@Autowired
	private ProductCategoryService productCategoryService;


	@GetMapping("/list")
	@Operation(summary = "获得商品分类列表")
	@PermitAll
	public Result<List<AppCategoryRespVO>> getProductCategoryList() {
		List<AppCategoryRespVO> list = productCategoryService.getEnableCategoryList();
		return Results.<List<AppCategoryRespVO>>success().data(list).build();
	}
}
