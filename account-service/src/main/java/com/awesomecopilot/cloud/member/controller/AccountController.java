package com.awesomecopilot.cloud.member.controller;

import com.awesomecopilot.common.lang.exception.BusinessException;
import com.awesomecopilot.common.lang.vo.Result;
import com.awesomecopilot.common.lang.vo.Results;
import com.awesomecopilot.cloud.member.dto.AccountDTO;
import com.awesomecopilot.cloud.member.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fox
 */
@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private AccountService accountService;

	@GetMapping("/")
	public Result getRemainAccount(@RequestParam("userId") String userId) {
		Integer remainAccount = accountService.getRemainAccount(userId);
		return Results.success().data(remainAccount).build();
	}

	@PostMapping("/reduce-balance")
	public Result reduceBalance(@RequestBody AccountDTO accountDTO, @RequestHeader(value = "Idempotent", required = false) String idempotent) {
		log.info("idempotent: {}", idempotent);
		try {
			accountService.reduceBalance(accountDTO.getUserId(), accountDTO.getPrice());
		}
		catch (BusinessException e) {
			return Results.fail()
					.message(e.getMessage())
					.build();
		}
		return Results.success().build();
	}
}

