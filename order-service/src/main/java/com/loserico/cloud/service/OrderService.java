package com.loserico.cloud.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.copilot.common.lang.exception.BusinessException;
import com.copilot.common.lang.vo.Result;
import com.copilot.orm.dao.CriteriaOperations;
import com.copilot.orm.dao.EntityOperations;
import com.copilot.orm.dao.SQLOperations;
import com.loserico.cloud.account.AccountFeignApi;
import com.loserico.cloud.dto.AccountDTO;
import com.loserico.cloud.dto.StorageDTO;
import com.loserico.cloud.entity.OrderEntity;
import com.loserico.cloud.storage.StorageFeignApi;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

import static com.copilot.json.jackson.JacksonUtils.toJson;


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
	private AccountFeignApi accountFeignApi;

	@Autowired
	private StorageFeignApi storageFeignApi;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private EntityOperations entityOperations;

	@Autowired
	private SQLOperations sqlOperations;

	@Autowired
	private CriteriaOperations criteriaOperations;

	//@Transactional
	@GlobalTransactional(name = "create-order", rollbackFor = Exception.class)
	public Long createOrder(String userId, String commodityCode, Integer count) {

		//logger.info("[createOrder] current XID: {}", RootContext.getXID());

		// deduct storage
		StorageDTO storageDTO = new StorageDTO();
		storageDTO.setCommodityCode(commodityCode);
		storageDTO.setCount(count);
		Result storageResult = storageFeignApi.reduceStock(storageDTO);
		if (!storageResult.success()) {
			throw new BusinessException(storageResult.getCode(), toJson(storageResult.getMessage()));
		}

		// deduct balance
		int price = count * 2;
		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setUserId(userId);
		accountDTO.setPrice(price);
		Result accountResult = accountFeignApi.reduceBalance(accountDTO);
		if (!accountResult.success()) {
			throw new BusinessException(accountResult.getCode(), toJson(accountResult.getMessage()));
		}
		// save order
		OrderEntity order = new OrderEntity();
		order.setUserId(userId);
		order.setCommodityCode(commodityCode);
		order.setCount(count);
		order.setMoney(price);
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
