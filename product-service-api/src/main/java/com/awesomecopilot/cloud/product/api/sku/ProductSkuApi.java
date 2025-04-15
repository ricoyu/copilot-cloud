package com.awesomecopilot.cloud.product.api.sku;

import com.awesomecopilot.cloud.product.api.constants.ApiConstants;
import com.awesomecopilot.cloud.product.api.dto.ProductSkuRespDTO;
import com.awesomecopilot.common.lang.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = ApiConstants.NAME, path = "/product/sku", contextId = "product-api")
public interface ProductSkuApi {

	@GetMapping("/get")
	@Operation(summary = "查询 SKU 信息")
	@Parameter(name = "id", description = "SKU 编号", required = true, example = "1024")
	Result<ProductSkuRespDTO> getSku(@RequestParam("id") Long id);
}
