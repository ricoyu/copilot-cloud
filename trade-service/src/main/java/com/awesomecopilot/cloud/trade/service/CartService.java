package com.awesomecopilot.cloud.trade.service;

import com.awesomecopilot.cloud.product.api.dto.ProductSkuRespDTO;
import com.awesomecopilot.cloud.product.api.sku.ProductSkuApi;
import com.awesomecopilot.cloud.trade.entity.TradeCart;
import com.awesomecopilot.cloud.trade.vo.AppCartAddReqVO;
import com.awesomecopilot.common.lang.exception.BusinessException;
import com.awesomecopilot.common.lang.vo.Result;
import com.awesomecopilot.orm.dao.CriteriaOperations;
import com.awesomecopilot.orm.dao.EntityOperations;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartService {

	@Autowired
	private EntityOperations entityOperations;

	@Autowired
	private CriteriaOperations criteriaOperations;

	@Autowired
	private ProductSkuApi productSkuApi;

	public Long addCart(long userId, @Valid AppCartAddReqVO cartAddReqVO) {
		TradeCart cart = criteriaOperations.query(TradeCart.class)
				.eq("userId", userId)
				.eq("skuId", cartAddReqVO.getSkuId())
				.findOne();

		Integer count = cartAddReqVO.getCount();
		ProductSkuRespDTO sku = checkProductSku(cartAddReqVO.getSkuId(), count);

		// 情况一：存在，则进行数量更新
		if (cart != null) {
			cart.setCount(cart.getCount() + count);
			cart.setSelected(true);
			//persist状态的entity, 不需要显式更新
		} else {
			// 情况二：不存在，则进行插入
			cart = new TradeCart().setUserId(userId)
							.setSpuId(sku.getSpuId())
									.setSkuId(sku.getId())
											.setSelected(true)
													.setCount(count);
			entityOperations.persist(cart);
		}

		return cart.getId();
	}

	private ProductSkuRespDTO checkProductSku(Long skuId, Integer count) {
		Result<ProductSkuRespDTO> skuResult = productSkuApi.getSku(skuId);
		ProductSkuRespDTO sku = skuResult.getCheckedData();
		if (sku == null) {
			throw new BusinessException("1_008_006_000", "商品 SKU 不存在");
		}
		if (count > sku.getStock()) {
			throw new BusinessException("1_008_006_004", "商品 SKU 库存不足");
		}

		return sku;
	}
}
