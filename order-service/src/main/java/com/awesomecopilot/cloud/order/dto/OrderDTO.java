package com.awesomecopilot.cloud.order.dto;

import lombok.Data;

@Data
public class OrderDTO {

	private String userId;

	private String commodityCode;

	private int count;

	private int money;
}
