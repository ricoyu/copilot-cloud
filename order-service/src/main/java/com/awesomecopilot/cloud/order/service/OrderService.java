package com.awesomecopilot.cloud.order.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.awesomecopilot.cloud.member.AccountFeignApi;
import com.awesomecopilot.cloud.common.dto.AccountDTO;
import com.awesomecopilot.cloud.common.dto.StorageDTO;
import com.awesomecopilot.cloud.order.dto.OrderDTO;
import com.awesomecopilot.cloud.order.entity.OrderEntity;
import com.awesomecopilot.cloud.trade.StorageFeignApi;
import com.awesomecopilot.common.lang.exception.BusinessException;
import com.awesomecopilot.common.lang.vo.Result;
import com.awesomecopilot.orm.dao.CriteriaOperations;
import com.awesomecopilot.orm.dao.EntityOperations;
//import io.seata.spring.annotation.GlobalTransactional;
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
	private AccountFeignApi accountFeignApi;

	@Autowired
	private StorageFeignApi storageFeignApi;

	@Autowired
	private EntityOperations entityOperations;

	@Autowired
	private CriteriaOperations criteriaOperations;

	@Transactional
	//@GlobalTransactional(rollbackFor = Exception.class)
	public Long createOrder(OrderDTO orderDTO) {
		//String storageUrl = "http://localhost:8086/storage/reduce-stock";
		String storageUrl = "http://storage-service/storage/reduce-stock";
		StorageDTO storageDTO = new StorageDTO();
		storageDTO.setCommodityCode(orderDTO.getCommodityCode());
		storageDTO.setCount(orderDTO.getCount());

		//Result<StorageDTO> storageResult = storageFeignApi.reduceStock(storageDTO);
		Result storageResult = restTemplate.postForObject(storageUrl, orderDTO, Result.class);

		//测试用HttpUtils完成restTemplate所做的事
		//Result storageResult = HttpUtils.post(storageUrl)
		//		.body(storageDTO)
		//		.responseType(Result.class)
		//		.request();

		if (!storageResult.isSuccess()) {
			throw new BusinessException(storageResult.getMessage().toString());
		}

		String accountUrl = "http://account-service/account/reduce-balance";
		//String accountUrl = "http://localhost:8083/account/reduce-balance";
		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setUserId(orderDTO.getUserId());
		accountDTO.setPrice(orderDTO.getMoney());

		//Result accountResult = accountFeignApi.reduceBalance(accountDTO);
		Result accountResult = restTemplate.postForObject(accountUrl, accountDTO, Result.class);

		//测试用HttpUtils完成restTemplate所做的事
		//Result accountResult = HttpUtils.post(accountUrl)
		//		.body(accountDTO)
		//		.responseType(Result.class)
		//		.request();

		if (!accountResult.isSuccess()) {
			throw new BusinessException(accountResult.getMessage().toString());
		}
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
