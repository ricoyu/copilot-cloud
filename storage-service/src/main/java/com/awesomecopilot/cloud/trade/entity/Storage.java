package com.awesomecopilot.cloud.trade.entity;

import com.awesomecopilot.orm.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "storage")
public class Storage extends BaseEntity {

    @Column(name = "commodity_code")
    private String commodityCode;
    
    @Column
    private Integer count;
    
}
