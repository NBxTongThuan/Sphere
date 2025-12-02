package com.sphere.tongthuan.APIGateway.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    WebClient webClient(){
        return WebClient.builder()
                .baseUrl("http://localhost:8080/auth-service")
                .build();
    }

}
