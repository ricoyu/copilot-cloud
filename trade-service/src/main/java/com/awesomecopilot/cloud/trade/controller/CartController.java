package com.awesomecopilot.cloud.trade.controller;

import com.awesomecopilot.cloud.trade.service.CartService;
import com.awesomecopilot.cloud.trade.vo.AppCartAddReqVO;
import com.awesomecopilot.common.lang.vo.Result;
import com.awesomecopilot.common.lang.vo.Results;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 购物车
 * <p/>
 * Copyright: Copyright (c) 2025-04-14 17:49
 * <p/>
 * Company: Sexy Uncle Inc.
 * <p/>
 *
 * @author Rico Yu  ricoyu520@gmail.com
 * @version 1.0
 */
@Slf4j
@Validated
@Tag(name = "购物车")
@RestController
@RequestMapping("/trade/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@PostMapping("/add")
	@Operation(summary = "添加购物车商品")
	public Result<Long> addCart(@Valid @RequestBody AppCartAddReqVO cartAddReqVO) {
		Long id = cartService.addCart(247L, cartAddReqVO);
		return Results.<Long>success().data(id).build();
	}
}
