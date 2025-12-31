package com.sphere.tongthuan.UserService.configuration;

import com.nimbusds.jose.JWSAlgorithm;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	private final String[] PUBLIC_ENDPOINTS = {

	};

	@NonFinal
	@Value("${jwt.signerKey}")
	String SECRET_KEY;

	@Bean
	PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder(10);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.authorizeHttpRequests(
				request ->
					request.requestMatchers(HttpMethod.POST, "/auth/register","/auth/login", "/auth/logout", "/auth/introspect").permitAll()
						.anyRequest().authenticated()
				)
			.oauth2ResourceServer(
				OAuth2ResourceServer ->
				OAuth2ResourceServer.jwt(jwtConfigurer ->
					jwtConfigurer
						.decoder(jwtDecoder())
						.jwtAuthenticationConverter(jwtAuthenticationConverter()))
					.authenticationEntryPoint(new JwtAuthenticationEntryPoint())
				)
			.csrf(CsrfConfigurer::disable);

		return httpSecurity.build();
	}

	@Bean
	JwtDecoder jwtDecoder(){
		SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), JWSAlgorithm.HS512.getName());
		return NimbusJwtDecoder
			.withSecretKey(secretKeySpec)
			.macAlgorithm(MacAlgorithm.HS512)
			.build();
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
