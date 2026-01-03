package com.sphere.tongthuan.APIGateway.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sphere.tongthuan.APIGateway.dto.ResponseTemplate;
import com.sphere.tongthuan.APIGateway.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationFilter implements GlobalFilter, Ordered {

	AuthenticationService authenticationService;
	ObjectMapper objectMapper;

	@NonFinal
	String[] publicEndPoints = {
		"/user-service/auth/login",
		"/user-service/user/register"
	};

	@NonFinal
	@Value(value = "${app.api-prefix}")
	private String apiPrefix;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		log.info(" request {}",exchange.getRequest().getURI());

		if(isPublicEndpoint(exchange.getRequest()))
			return chain.filter(exchange);

		List<String> authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);

		if(CollectionUtils.isEmpty(authHeader))
			return unauthenticated(exchange.getResponse());

		String token = authHeader.getFirst().replace("Bearer ", "");

		log.info("token {}", token);

		return authenticationService.introspect(token).flatMap(
			introspectSP ->
			{
				if(introspectSP.getResult().isValid())
					return chain.filter(exchange);
				else {
					return unauthenticated(exchange.getResponse());
				}
			}).onErrorResume(
				throwable -> unauthenticated(exchange.getResponse())
		);
	}

	@Override
	public int getOrder() {
		return 0;
	}

	private boolean isPublicEndpoint(ServerHttpRequest request)
	{

		log.info("path {}", request.getURI().getPath());

		return Arrays.stream(publicEndPoints).anyMatch(
			s -> request.getURI().getPath().matches(apiPrefix+s)
		);
	}

	Mono<Void> unauthenticated(ServerHttpResponse serverHttpResponse) {
		ResponseTemplate<?> responseTemplate = ResponseTemplate.builder()
			.code(1200)
			.message("Unauthenticated")
			.build();

		String body;
		try {
			body = objectMapper.writeValueAsString(responseTemplate);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
		serverHttpResponse.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		return serverHttpResponse.writeWith(Mono.just(serverHttpResponse.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8))));
	}


}
