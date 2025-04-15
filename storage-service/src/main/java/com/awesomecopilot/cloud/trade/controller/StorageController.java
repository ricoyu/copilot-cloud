package com.awesomecopilot.cloud.trade.controller;

import com.awesomecopilot.cloud.feign.annotation.Idempotent;
import com.awesomecopilot.cloud.trade.dto.StorageDTO;
import com.awesomecopilot.cloud.trade.service.StorageService;
import com.awesomecopilot.common.lang.vo.Result;
import com.awesomecopilot.common.lang.vo.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public Result<StorageDTO> reduceStock(@RequestBody StorageDTO storageDTO) {
		log.info("调用到这个instance");
		storageService.deduct(storageDTO.getCommodityCode(), storageDTO.getCount());
		return Results.<StorageDTO>success().data(storageDTO).build();
	}

	/**
	 * 获取库存余额
	 * @param commodityCode
	 * @return
	 */
	@GetMapping("/")
	public Result getRemainCount(@RequestParam("commodityCode") String commodityCode) {
		Integer remainCount = storageService.getRemainCount(commodityCode);
		return Results.success().data(remainCount).build();
	}
}
