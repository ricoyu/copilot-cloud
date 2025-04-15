package com.awesomecopilot.cloud.trade;

import com.awesomecopilot.common.lang.vo.Result;
import com.awesomecopilot.cloud.common.dto.StorageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "storage-service",contextId = "storage-api", path = "/storage")
public interface StorageFeignApi {


	/**
	 * 扣减库存
	 *
	 * @param storageDTO
	 * @return
	 */
	@PostMapping("/reduce-stock")
	public Result<StorageDTO> reduceStock(@RequestBody StorageDTO storageDTO);

	/**
	 * 获取库存余额
	 * @param commodityCode
	 * @return
	 */
	@GetMapping("/")
	public Result getRemainCount(@RequestParam("commodityCode") String commodityCode);
}
