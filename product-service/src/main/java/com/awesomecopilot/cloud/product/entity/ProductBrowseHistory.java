package com.awesomecopilot.cloud.product.entity;

import com.awesomecopilot.orm.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 商品浏览记录表实体类
 */
@Data
@Entity
@Table(name = "product_browse_history", indexes = {
    @Index(name = "idx_spuId", columnList = "spu_id"),
    @Index(name = "idx_userId", columnList = "user_id")
})
public class ProductBrowseHistory extends BaseEntity {

    /**
     * 用户编号
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 商品 SPU 编号
     */
    @Column(name = "spu_id", nullable = false)
    private Long spuId;

    /**
     * 用户是否删除
     */
    @Column(name = "user_deleted", nullable = false)
    private Boolean userDeleted = false;

    /**
     * 创建者
     */
    @Column(name = "creator", length = 64)
    private String creator = "";

    /**
     * 更新者
     */
    @Column(name = "updater", length = 64)
    private String updater = "";

    /**
     * 是否删除
     */
    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    /**
     * 租户编号
     */
    @Column(name = "tenant_id", nullable = false)
    private Long tenantId = 0L;
}