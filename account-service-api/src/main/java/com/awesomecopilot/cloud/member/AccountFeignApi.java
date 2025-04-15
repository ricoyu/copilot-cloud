package com.awesomecopilot.cloud.member;

import com.awesomecopilot.common.lang.vo.Result;
import com.awesomecopilot.cloud.common.dto.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "account-service", contextId = "account-api", path = "/account")
public interface AccountFeignApi {


	@GetMapping("/")
	public Result getRemainAccount(@RequestParam("userId") String userId);

	@PostMapping("/reduce-balance")
	public Result reduceBalance(@RequestBody AccountDTO accountDTO);
}
