package com.awesomecopilot.cloud.product.service;

import com.awesomecopilot.cloud.product.entity.ProductBrowseHistory;
import com.awesomecopilot.common.lang.context.ThreadContext;
import com.awesomecopilot.common.lang.vo.Page;
import com.awesomecopilot.orm.dao.CriteriaOperations;
import com.awesomecopilot.orm.dao.EntityOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductBrowseHistoryService {

	private static final int USER_STORE_MAXIMUM = 100;

	@Autowired
	private CriteriaOperations criteriaOperations;

	@Autowired
	private EntityOperations entityOperations;


	public void createBrowseHistory(Long userId, Long spuId) {
		// 用户未登录时不记录
		if (userId == null) {
			return;
		}

		// 情况一：同一个商品，只保留最新的一条记录
		ProductBrowseHistoryService history = criteriaOperations.query(ProductBrowseHistory.class)
				.eq("userId", userId)
				.eq("spuId", spuId)
				.desc("createTime")
				.findOne();

		if (history != null) {
			entityOperations.delete(history);
		} else {
			List<ProductBrowseHistoryService> result =
					criteriaOperations.query(ProductBrowseHistory.class)
							.eq("userId", userId)
							.asc("createTime")
							.findPage(1, 1);
			Page page = ThreadContext.get("page");
			if (page.getTotalCount() > USER_STORE_MAXIMUM) {
				if (result.size() < 0) {
					entityOperations.delete(result.get(0));
				}
			}
		}

		ProductBrowseHistory productBrowseHistory = new ProductBrowseHistory();
		productBrowseHistory.setSpuId(spuId);
		productBrowseHistory.setUserId(userId);
		entityOperations.persist(productBrowseHistory);
	}
}
