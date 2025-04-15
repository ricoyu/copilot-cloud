package com.awesomecopilot.cloud.product.config;

import com.awesomecopilot.orm.dao.JpaDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * Copyright: (C), 2022-09-07 13:09
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Configuration
public class JpaConfig {
	
	@Bean
	public JpaDao jpaDao() {
		return new JpaDao();
	}
}
