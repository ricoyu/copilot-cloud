package com.awesomecopilot.cloud.controller;

import com.awesomecopilot.cloud.dto.StorageDTO;
import com.awesomecopilot.cloud.feign.annotation.Idempotent;
import com.awesomecopilot.cloud.order.service.StorageService;
import com.awesomecopilot.common.lang.vo.Result;
import com.awesomecopilot.common.lang.vo.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/storage")
public class StorageController {

	@Autowired
	private StorageService storageService;

	/**
	 * 扣减库存
	 *
	 * @param storageDTO
	 * @return
	 */
	@Idempotent
	@PostMapping("/reduce-stock")
	public Result reduceStock(@RequestBody StorageDTO storageDTO, @RequestHeader(value = "Idempotent", required = false) String idempotent) {
		log.info("idempotent: {}", idempotent);
		storageService.deduct(storageDTO.getCommodityCode(), storageDTO.getCount());
		return Results.success().build();
	}

	/**
	 * 获取库存余额
	 * @param commodityCode
	 * @return
	 */
	@GetMapping("/")
	public Result getRemainCount(@RequestParam("commodityCode") String commodityCode) {
		Integer remainCount = storageService.getRemainCount(commodityCode);
		return Results.success().result(remainCount);
	}
}
