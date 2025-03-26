package com.copilot.cloud.oauth.handler;

import com.copilot.common.lang.vo.Result;
import com.copilot.common.lang.vo.Results;
import com.copilot.web.utils.RestUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.copilot.common.lang.errors.ErrorTypes.TOKEN_EXPIRED;

@Slf4j
@Component("restAuthenticationEntryPoint")
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	@Override
	public void commence(
			HttpServletRequest request,
			HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		
		Result result = Results.status(TOKEN_EXPIRED).build();
		RestUtils.writeJson(response, result);
	}
}