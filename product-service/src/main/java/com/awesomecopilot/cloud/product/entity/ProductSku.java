package com.awesomecopilot.cloud.product.entity;

import com.awesomecopilot.orm.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品SKU实体类
 */
@Data
@Entity
@Table(name = "product_sku")
public class ProductSku extends BaseEntity {

    /**
     * spu编号
     */
    @Column(name = "spu_id", nullable = false)
    private Long spuId;

    /**
     * 属性数组，JSON 格式 [{propertId: , valueId: }, {propertId: , valueId: }]
     */
    @Column(name = "properties", length = 512)
    private String properties;

    /**
     * 商品价格，单位：分
     */
    @Column(name = "price", nullable = false)
    private Integer price = -1;

    /**
     * 市场价，单位：分
     */
    @Column(name = "market_price")
    private Integer marketPrice;

    /**
     * 成本价，单位：分
     */
    @Column(name = "cost_price", nullable = false)
    private Integer costPrice = -1;

    /**
     * SKU 的条形码
     */
    @Column(name = "bar_code", length = 64)
    private String barCode;

    /**
     * 图片地址
     */
    @Column(name = "pic_url", nullable = false, length = 256)
    private String picUrl;

    /**
     * 库存
     */
    @Column(name = "stock")
    private Integer stock;

    /**
     * 商品重量，单位：kg 千克
     */
    @Column(name = "weight")
    private Double weight;

    /**
     * 商品体积，单位：m^3 平米
     */
    @Column(name = "volume")
    private Double volume;

    /**
     * 一级分销的佣金，单位：分
     */
    @Column(name = "first_brokerage_price")
    private Integer firstBrokeragePrice;

    /**
     * 二级分销的佣金，单位：分
     */
    @Column(name = "second_brokerage_price")
    private Integer secondBrokeragePrice;

    /**
     * 商品销量
     */
    @Column(name = "sales_count")
    private Integer salesCount;

    /**
     * 创建人
     */
    @Column(name = "creator", length = 64)
    private String creator;

    /**
     * 更新人
     */
    @Column(name = "updater")
    private BigDecimal updater;

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