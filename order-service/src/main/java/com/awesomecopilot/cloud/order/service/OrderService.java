package com.awesomecopilot.cloud.order.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.awesomecopilot.cloud.order.dto.OrderDTO;
import com.awesomecopilot.cloud.order.entity.OrderEntity;
import com.awesomecopilot.orm.dao.CriteriaOperations;
import com.awesomecopilot.orm.dao.EntityOperations;
import com.awesomecopilot.orm.dao.SQLOperations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 订单服务
 * <p/>
 * Copyright: Copyright (c) 2024-12-14 19:04
 * <p/>
 * Company: Sexy Uncle Inc.
 * <p/>
 *
 * @author Rico Yu  ricoyu520@gmail.com
 * @version 1.0
 */
@Service
@Slf4j
public class OrderService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private EntityOperations entityOperations;

	@Autowired
	private SQLOperations sqlOperations;

	@Autowired
	private CriteriaOperations criteriaOperations;

	@Transactional
	public Long createOrder(OrderDTO orderDTO) {
		// save order
		OrderEntity order = new OrderEntity();
		order.setUserId(orderDTO.getUserId());
		order.setCommodityCode(orderDTO.getCommodityCode());
		order.setCount(orderDTO.getCount());
		order.setMoney(orderDTO.getMoney());
		order.setCreateTime(LocalDateTime.now());
		order.setUpdateTime(LocalDateTime.now());
		entityOperations.persist(order);
		log.info("[createOrder] orderId: {}", order.getId());

		return order.getId();
	}

	@SentinelResource("/service/findOrderByUserId")
	public List<OrderEntity> findOrderByUserId(String userId) {
		List<OrderEntity> orders = criteriaOperations.findList(OrderEntity.class, "userId", userId);
		return orders;
	}
}
