package com.awesomecopilot.cloud.product;

import com.awesomecopilot.common.lang.vo.Result;
import com.awesomecopilot.common.lang.vo.Results;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class JacksonTest {

	@Test
	public void testSerialzieListStrInPojo() throws JsonProcessingException {
		Spu spu = new Spu();
		//spu.setBirthday(LocalDateTime.now());
		spu.setName("test");
		List<String> urls = new ArrayList<>();
		urls.add("http://127.0.0.1:48080/admin-api/infra/file/4/get/d8c88ec4b865073945319d638f7159170b5dff988b9119642edcdb04eba7fe72.jpg");
		spu.setPicUrils(urls);
		Result<Spu> result = Results.<Spu>success().data(spu).build();
		//String json = toJson(result);
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(result);
		System.out.println(json);
	}

	@Schema(description = "用户 App - 商品 SPU Response VO")
	@Data
	class Spu {
		private String name;

		//private LocalDateTime birthday;

		private List<String> picUrils;
	}
}
