package com.awesomecopilot.cloud.member.service;

import com.awesomecopilot.common.lang.exception.BusinessException;
import com.awesomecopilot.orm.dao.EntityOperations;
import com.awesomecopilot.orm.dao.SQLOperations;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Fox
 */
@Service
@Slf4j
public class AccountService{
    
    @Autowired
    private SQLOperations sqlOperations;
    
    @Autowired
    private EntityOperations entityOperations;

    /**
     * 扣减账余额
     * @param userId
     * @param price
     * @throws BusinessException
     */
    @Transactional
    public void reduceBalance(String userId, Integer price) throws BusinessException {
        log.info("[reduceBalance] currenet XID: {}", RootContext.getXID());

        checkBalance(userId, price);

        LocalDateTime updateTime = LocalDateTime.now();
        Map<String, Object> params = new HashMap<>();
        params.put("price", price);
        params.put("userId", userId);
        params.put("updateTime", updateTime);
        int updateCount =
                sqlOperations.execute("UPDATE account SET money = money - :price,update_time = :updateTime WHERE user_id = :userId AND money >= :price",
                        params);
        if (updateCount == 0) {
            throw new BusinessException("扣减账户余额失败");
        }
    }

    /**
     * 获取账户余额
     * @param userId
     * @return
     */
    public Integer getRemainAccount(String userId) {
        return sqlOperations.findOne("selectByUserId", "userId", userId);
    }

    private void checkBalance(String userId, Integer price) throws BusinessException {
        Integer balance = sqlOperations.findOne("SELECT money FROM account WHERE user_id = :userId", "userId", userId);
        if (balance < price) {
            throw new BusinessException("余额不足");
        }
    }
}
