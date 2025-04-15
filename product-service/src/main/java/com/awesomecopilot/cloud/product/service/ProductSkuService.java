package com.awesomecopilot.cloud.product.service;

import com.awesomecopilot.cloud.product.entity.ProductSku;
import com.awesomecopilot.orm.dao.CriteriaOperations;
import com.awesomecopilot.orm.dao.EntityOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductSkuService {

	@Autowired
	private EntityOperations entityOperations;

	@Autowired
	private CriteriaOperations criteriaOperations;

	public List<ProductSku> getSkuListBySpuId (Long spuId) {
		return criteriaOperations.findList(ProductSku.class, "spuId", spuId);
	}

	public ProductSku getSku(Long skuId) {
		return entityOperations.get(ProductSku.class, skuId);
	}
}
