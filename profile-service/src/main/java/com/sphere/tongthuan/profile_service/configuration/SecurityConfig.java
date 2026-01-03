package com.sphere.tongthuan.profile_service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	private final String[] PUBLIC_ENDPOINTS = {

	};

	private final CustomJwtDecoder customJwtDecoder;

	public SecurityConfig(CustomJwtDecoder customJwtDecoder) {
		this.customJwtDecoder = customJwtDecoder;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.authorizeHttpRequests(
				request ->
					request.requestMatchers(HttpMethod.POST, "/internal/user-profile/create").permitAll()
						.anyRequest().authenticated()
				)
			.oauth2ResourceServer(
				OAuth2ResourceServer ->
				OAuth2ResourceServer.jwt(jwtConfigurer ->
					jwtConfigurer
						.decoder(customJwtDecoder)
						.jwtAuthenticationConverter(jwtAuthenticationConverter()))

				)
			.csrf(CsrfConfigurer::disable);

		return httpSecurity.build();
	}

	@Bean
	JwtAuthenticationConverter jwtAuthenticationConverter()
	{
		JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

		jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);


		return jwtAuthenticationConverter;
	}

	@Bean
	CorsFilter corsFilter()
	{
		CorsConfiguration corsConfiguration =  new CorsConfiguration();

		corsConfiguration.addAllowedOrigin("http://localhost:3000");
		corsConfiguration.addAllowedMethod("*");
		corsConfiguration.addAllowedHeader("*");

		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}




}
