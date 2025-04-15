package com.awesomecopilot.cloud.trade.entity;

import com.awesomecopilot.orm.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "trade_cart")
@Where(clause = "deleted = false")  // 查询自动过滤已删除数据
@SQLDelete(sql = "UPDATE trade_cart SET deleted = true WHERE id = ?")  // 物理删除转逻辑删除
public class TradeCart extends BaseEntity {

    /** 用户编号 */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /** 商品 SPU 编号 */
    @Column(name = "spu_id", nullable = false)
    private Long spuId;

    /** 商品 SKU 编号 */
    @Column(name = "sku_id", nullable = false)
    private Long skuId;

    /** 商品购买数量 */
    @Column(name = "count", nullable = false)
    private Integer count;

    /** 是否选中 */
    @Column(name = "selected", nullable = false)
    private Boolean selected = true;

    /** 创建者 */
    @Column(name = "creator", length = 64)
    private String creator;


    /** 更新者 */
    @Column(name = "updater", length = 64)
    private String updater;

    /** 是否删除 */
    @Column(name = "deleted", nullable = false, columnDefinition = "BIT(1) default 0")
    private Boolean deleted = false;

    /** 租户编号 */
    @Column(name = "tenant_id", nullable = false)
    private Long tenantId = 0L;
    
}