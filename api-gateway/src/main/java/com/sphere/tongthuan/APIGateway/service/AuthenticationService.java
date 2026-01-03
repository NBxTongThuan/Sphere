package com.sphere.tongthuan.APIGateway.service;

import com.sphere.tongthuan.APIGateway.dto.ResponseTemplate;
import com.sphere.tongthuan.APIGateway.dto.clientDtoRequest.IntrospectRequest;
import com.sphere.tongthuan.APIGateway.dto.clientDtoResponse.IntrospectResponse;
import com.sphere.tongthuan.APIGateway.repository.UserServiceClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
	UserServiceClient userServiceClient;

	public Mono<ResponseTemplate<IntrospectResponse>> introspect(String token){
		return userServiceClient.introspect(
			IntrospectRequest.builder()
				.accessToken(token)
				.build()
		);
	}

}
