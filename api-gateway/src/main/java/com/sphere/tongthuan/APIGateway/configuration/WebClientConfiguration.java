package com.sphere.tongthuan.APIGateway.configuration;

import com.sphere.tongthuan.APIGateway.repository.UserServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfiguration {

    @Bean
    WebClient webClient(){
        return WebClient.builder()
                .baseUrl("http://localhost:8080/user-service")
                .build();
    }

    @Bean
    UserServiceClient userServiceClient(WebClient webClient)
    {
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(
            WebClientAdapter.create(webClient)).build();
        return httpServiceProxyFactory.createClient(UserServiceClient.class);

    }

}
