package com.sphere.tongthuan.APIGateway.repository;

import com.sphere.tongthuan.APIGateway.dto.ResponseTemplate;
import com.sphere.tongthuan.APIGateway.dto.clientDtoRequest.IntrospectRequest;
import com.sphere.tongthuan.APIGateway.dto.clientDtoResponse.IntrospectResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

public interface UserServiceClient {

	@PostExchange(url = "/internal/auth/introspect", contentType = MediaType.APPLICATION_JSON_VALUE)
	Mono<ResponseTemplate<IntrospectResponse>> introspect(@RequestBody IntrospectRequest introspectRequest);

}
