package com.sphere.tongthuan.UserService.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sphere.tongthuan.UserService.dto.ResponseTemplate;
import com.sphere.tongthuan.UserService.exception.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

		ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;

		response.setStatus(errorCode.getHttpStatusCode().value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		ResponseTemplate<?> responseTemplate = ResponseTemplate.builder()
			.code(errorCode.getCode())
			.message(errorCode.getMessage())
			.build();

		ObjectMapper objectMapper = new ObjectMapper();

		response.getWriter().write(objectMapper.writeValueAsString(responseTemplate));
		response.flushBuffer();

	}
}
