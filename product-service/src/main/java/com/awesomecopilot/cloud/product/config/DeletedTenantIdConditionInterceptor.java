package com.awesomecopilot.cloud.product.config;

import com.awesomecopilot.common.lang.utils.SqlUtils;
import org.hibernate.resource.jdbc.spi.StatementInspector;

/**
 * 按需添加deleted=0的条件 和tenant_id=xxx的条件
 * <p>
 * 对SQLOperations和CriteriaOperations都有效
 * <p/>
 * Copyright: Copyright (c) 2025-04-08 12:32
 * <p/>
 * Company: Sexy Uncle Inc.
 * <p/>

 * @author Rico Yu  ricoyu520@gmail.com
 * @version 1.0
 */
public class DeletedTenantIdConditionInterceptor implements StatementInspector {
    @Override
    public String inspect(String sql) {
        // 在已有WHERE后追加条件，或新增WHERE
        return SqlUtils.addDeleteTenantIdCondition(sql);
    }
}