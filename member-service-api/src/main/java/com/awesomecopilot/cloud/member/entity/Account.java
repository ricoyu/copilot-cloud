package com.awesomecopilot.cloud.member.entity;

import com.awesomecopilot.orm.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "account")
public class Account extends BaseEntity {
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "money")
	private Integer money;
}

