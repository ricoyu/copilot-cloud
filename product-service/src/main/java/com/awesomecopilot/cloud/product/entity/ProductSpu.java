package com.awesomecopilot.cloud.product.entity;

import com.awesomecopilot.orm.converter.StringListConverter;
import com.awesomecopilot.orm.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "product_spu")
public class ProductSpu extends BaseEntity {

    /**
     * 商品名称
     */
    @Column(name = "name", nullable = false, length = 128)
    private String name;

    /**
     * 关键字
     */
    @Column(name = "keyword", length = 256)
    private String keyword;

    /**
     * 商品简介
     */
    @Column(name = "introduction", length = 256)
    private String introduction;

    /**
     * 商品详情
     */
    @Lob
    @Column(name = "description")
    private String description;

    /**
     * 商品分类编号
     */
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    /**
     * 商品品牌编号
     */
    @Column(name = "brand_id")
    private Integer brandId;

    /**
     * 商品封面图
     */
    @Column(name = "pic_url", nullable = false, length = 256)
    private String picUrl;

    /**
     * 商品轮播图地址数组，以逗号分隔，最多上传15张
     */
    @Column(name = "slider_pic_urls", length = 2000)
    @Convert(converter = StringListConverter.class)
    private List<String> sliderPicUrls;

    /**
     * 排序字段
     */
    @Column(name = "sort", nullable = false)
    private Integer sort = 0;

    /**
     * 商品状态: 1 上架（开启） 0 下架（禁用） -1 回收
     */
    @Column(name = "status", nullable = false)
    private Integer status;

    /**
     * 规格类型：0 单规格 1 多规格
     */
    @Column(name = "spec_type")
    private Boolean specType;

    /**
     * 商品价格，单位使用：分
     */
    @Column(name = "price", nullable = false)
    private Integer price = -1;

    /**
     * 市场价，单位使用：分
     */
    @Column(name = "market_price")
    private Integer marketPrice;

    /**
     * 成本价，单位：分
     */
    @Column(name = "cost_price", nullable = false)
    private Integer costPrice = -1;

    /**
     * 库存
     */
    @Column(name = "stock", nullable = false)
    private Integer stock = 0;

    /**
     * 配送方式数组
     */
    @Column(name = "delivery_types", nullable = false, length = 32)
    private String deliveryTypes = "";

    /**
     * 物流配置模板编号
     */
    @Column(name = "delivery_template_id")
    private Long deliveryTemplateId;

    /**
     * 赠送积分
     */
    @Column(name = "give_integral", nullable = false)
    private Integer giveIntegral = 0;

    /**
     * 分销类型
     */
    @Column(name = "sub_commission_type")
    private Boolean subCommissionType;

    /**
     * 商品销量
     */
    @Column(name = "sales_count")
    private Integer salesCount = 0;

    /**
     * 虚拟销量
     */
    @Column(name = "virtual_sales_count")
    private Integer virtualSalesCount = 0;

    /**
     * 商品点击量
     */
    @Column(name = "browse_count")
    private Integer browseCount = 0;

    /**
     * 创建人
     */
    @Column(name = "creator", length = 64)
    private String creator;

    /**
     * 更新人
     */
    @Column(name = "updater", length = 64)
    private String updater;

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