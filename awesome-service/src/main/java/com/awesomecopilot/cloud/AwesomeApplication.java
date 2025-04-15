package com.awesomecopilot.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <p>
 * Copyright: (C), 2022-07-22 17:12
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@SpringBootApplication
@EnableScheduling
public class AwesomeApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(AwesomeApplication.class, args);
	}
}
