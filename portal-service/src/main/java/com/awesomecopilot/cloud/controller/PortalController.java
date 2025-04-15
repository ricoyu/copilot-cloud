package com.awesomecopilot.cloud.controller;

import com.awesomecopilot.common.lang.concurrent.CopilotExecutors;
import com.awesomecopilot.common.lang.vo.Result;
import com.awesomecopilot.common.lang.vo.Results;
import com.awesomecopilot.common.spring.annotation.PostInitialize;
import com.awesomecopilot.cloud.api.AwesomeFeignApi;
import com.awesomecopilot.cloud.component.HttpClientPoolStats;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p/>
 * Copyright: Copyright (c) 2024-10-30 17:22
 * <p/>
 * Company: Sexy Uncle Inc.
 * <p/>
 *
 * @author Rico Yu  ricoyu520@gmail.com
 * @version 1.0
 */
@Slf4j
@RefreshScope
@RestController
@RequestMapping("/portal")
public class PortalController {

	@Value("${name}")
	private String name;

	@Value("${age}")
	private Integer age;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private AwesomeFeignApi awesomeApi;

	@Autowired
	private HttpClientPoolStats httpClientPoolStats;

	private AtomicInteger counter = new AtomicInteger(1);

	private static final ExecutorService POOL = CopilotExecutors.of("httpClientExecutePool")
			.corePoolSize(20)
			.maxPoolSize(500)
			.build();

	@PostInitialize
	public void init() {
		System.out.println("PostInitialize init");
	}

	@GetMapping("/lb-restTemplate")
	public Result restTemplateLB() {
		Map<String, Integer> map = new HashMap<>();
		for (int i = 0; i < 100; i++) {
			String port = restTemplate.getForObject("http://awesome-service/aws/port", String.class);
			Integer count = map.get(port);
			if (count == null) {
				map.put(port, 1);
			} else {
				map.put(port, count + 1);
			}
		}
		return Results.success().data(map).build();
	}

	@GetMapping("/info")
	public Result info() {
		return Results.success()
				.data("name: " + name + ", age: " + age)
				.build();
	}

	@GetMapping("/port")
	public Map<String, Integer> port() {
		Map<String, Integer> map = new HashMap<>();
		for (int i = 0; i < 100; i++) {
			String port = awesomeApi.port();
			//System.out.println(port);
			Integer count = map.get(port);
			if (count == null) {
				map.put(port, 1);
			} else {
				map.put(port, count + 1);
			}
		}
		return map;
	}

	@GetMapping("/timeout-port")
	public Map<String, Integer> timeout() {
		Map<String, Integer> map = new HashMap<>();
		for (int i = 0; i < 2; i++) {
			String port = awesomeApi.timeout();
			//System.out.println(port);
			Integer count = map.get(port);
			if (count == null) {
				map.put(port, 1);
			} else {
				map.put(port, count + 1);
			}
		}
		return map;
	}

	@GetMapping("/retry")
	public boolean retry() {
		return awesomeApi.retry(null);
	}


	@GetMapping("/pool-statistic")
	public Map<String, Integer> poolStatistic() {
		Map<String, Integer> map = new HashMap<>();
		for (int i = 0; i < 100; i++) {
			try {
				String port = awesomeApi.timeout();
				//System.out.println(port);
				Integer count = map.get(port);
				if (count == null) {
					map.put(port, 1);
				} else {
					map.put(port, count + 1);
				}
			} catch (Exception e) {

			}
			httpClientPoolStats.printPoolStats();
		}
		return map;
	}

	@GetMapping("/compression")
	public String compression() {
		awesomeApi.compression();
		return "";
	}

	@GetMapping("/excep")
	public Result exception() {
		counter.getAndIncrement();
		if (counter.get() %2 ==0) {
			int i = 1/0;
		}

		return Results.success().build();
	}
}
