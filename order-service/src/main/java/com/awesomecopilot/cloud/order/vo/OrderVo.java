package com.awesomecopilot.cloud.order.vo;

import lombok.Data;

@Data
public class OrderVo {
    private String userId;
    /**商品编号**/
    private String commodityCode;
    
    private Integer count;
    
    private Integer money;
}
