package com.awesomecopilot.cloud.product.entity;

import com.awesomecopilot.orm.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品分类
 * <p/>
 * Copyright: Copyright (c) 2025-04-03 15:44
 * <p/>
 * Company: Sexy Uncle Inc.
 * <p/>

 * @author Rico Yu  ricoyu520@gmail.com
 * @version 1.0
 */
@Data
@Entity
@Table(name = "product_category")
@EqualsAndHashCode(callSuper = false)
public class ProductCategory extends BaseEntity {


    /**
     * 父分类编号
     */
    @Column(name = "parent_id", nullable = false)
    private Long parentId;

    /**
     * 分类名称
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 移动端分类图
     */
    @Column(name = "pic_url", nullable = false)
    private String picUrl;

    /**
     * 分类排序
     */
    @Column(name = "sort")
    private Integer sort = 0;

    /**
     * 开启状态
     */
    @Column(name = "status", nullable = false)
    private Integer status;

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