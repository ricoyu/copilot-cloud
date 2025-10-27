package com.awesomecopilot.cloud.trade.service;

import com.awesomecopilot.cloud.trade.entity.Storage;
import com.awesomecopilot.common.lang.exception.BusinessException;
import com.awesomecopilot.orm.dao.EntityOperations;
import com.awesomecopilot.orm.dao.SQLOperations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class StorageService {

	@Autowired
	private SQLOperations sqlOperations;

	@Autowired
	private EntityOperations entityOperations;

	@Transactional
	public void deduct(String commodityCode, int count) {
		log.info("=============扣减库存=================");
		// 检查库存
		checkStock(commodityCode, count);

		log.info("开始扣减 {} 库存", commodityCode);
		Map<String, Object> params = new HashMap<>();
		params.put("commodityCode", commodityCode);
		params.put("count", count);
		Integer updateCount = sqlOperations.execute("reduceStorage", params);
		if (updateCount == null || updateCount == 0) {
			throw new BusinessException("库存不足");
		}
		log.info("扣减 {} 库存结果:{}", commodityCode, updateCount > 0 ? "操作成功" : "库存不足");
	}

	/**
	 * 剩余库存
	 *
	 * @param commodityCode
	 * @return
	 */
	public Integer getRemainCount(String commodityCode) {
		Integer stock = sqlOperations.findOne("findByCommodityCode", "commodityCode", commodityCode);
		return stock;
	}

	private void checkStock(String commodityCode, int count) {

		log.info("检查 {} 库存", commodityCode);
		String sql = """
				SELECT * FROM `storage` WHERE commodity_code = :commodityCode""";
		Storage storage = sqlOperations.findOne(sql, "commodityCode", commodityCode, Storage.class);
		if (storage == null) {
			throw new BusinessException("找不到商品ID: {} 的库存", commodityCode);
		}
		Integer remainCount = storage.getCount();

		if (remainCount < count) {
			log.warn("{} 库存不足，当前库存:{}", commodityCode, count);
			throw new BusinessException("库存不足");
		}

	}

	public List<Storage> list() {
		return entityOperations.findAll(Storage.class);
	}
}
