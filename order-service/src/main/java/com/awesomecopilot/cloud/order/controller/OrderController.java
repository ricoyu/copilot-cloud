package com.awesomecopilot.cloud.order.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.awesomecopilot.cloud.order.dto.OrderDTO;
import com.awesomecopilot.cloud.order.entity.OrderEntity;
import com.awesomecopilot.cloud.order.service.OrderService;
import com.awesomecopilot.common.lang.vo.Result;
import com.awesomecopilot.common.lang.vo.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * Copyright: (C), 2022-07-23 9:11
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/create")
	@SentinelResource("createOrder")
	public Result createOrder(@RequestBody OrderDTO orderDTO) {
		Long orderId = orderService.createOrder(orderDTO);
		return Results.success().data(orderId).build();
	}

	@GetMapping("/getOrder")
	public Result getOrder(@RequestParam("userId") String userId) {
			List<OrderEntity> orders = orderService.findOrderByUserId(userId);
		return Results.success().data(orders).build();
	}

	@GetMapping("/findOrderByUserId/{userId}")
	public Result findOrderByUserId(@PathVariable String userId) {
		List<OrderEntity> orders = orderService.findOrderByUserId(userId);
		return Results.success().data(orders).build();
	}

	@GetMapping("/findOrderByUserId2/{userId}")
	public Result findOrderByUserId2(@PathVariable String userId) {
		List<OrderEntity> orders = orderService.findOrderByUserId(userId);
		return Results.success().data(orders).build();
	}

}
