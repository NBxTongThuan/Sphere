package com.sphere.tongthuan.UserService.service.impl;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import com.sphere.tongthuan.UserService.dto.ResponseTemplate;
import com.sphere.tongthuan.UserService.dto.request.IntrospectRequest;
import com.sphere.tongthuan.UserService.dto.request.LoginRequest;
import com.sphere.tongthuan.UserService.dto.request.LogoutRequest;
import com.sphere.tongthuan.UserService.dto.response.IntrospectResponse;
import com.sphere.tongthuan.UserService.dto.response.LoginResponse;
import com.sphere.tongthuan.UserService.entity.InvalidAccessToken;
import com.sphere.tongthuan.UserService.exception.AppException;
import com.sphere.tongthuan.UserService.exception.ErrorCode;
import com.sphere.tongthuan.UserService.mapper.UserMapper;
import com.sphere.tongthuan.UserService.repository.InvalidAccessTokenRepository;
import com.sphere.tongthuan.UserService.repository.UserRepository;
import com.sphere.tongthuan.UserService.service.AuthenticationService;
import com.sphere.tongthuan.UserService.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements AuthenticationService {


	@NonFinal
	@Value("${app.token.max-refresh-token}")
	long MAX_REFRESH_TOKEN;

	UserRepository userRepository;

	UserMapper userMapper;

	JwtUtil jwtUtil;

	InvalidAccessTokenRepository invalidAccessTokenRepository;

	@Override
	public ResponseTemplate<LoginResponse> login(LoginRequest loginRequest, HttpServletResponse response) throws JOSEException {
		var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(
			() -> new AppException(ErrorCode.INVALID_EMAIL)
		);

		String token = jwtUtil.generateAccessToken(user);
		return ResponseTemplate.<LoginResponse>builder()
			.message("success")
			.result(
				LoginResponse.builder()
					.accessToken(token)
					.build()
			)
			.build();
	}

	@Override
	public void logout(LogoutRequest logoutRequest) throws ParseException, JOSEException {

		if (!jwtUtil.verifyToken(logoutRequest.getAccessToken()))
			throw new AppException(ErrorCode.UNAUTHENTICATED);

		SignedJWT signedJWT = SignedJWT.parse(logoutRequest.getAccessToken());

		invalidAccessTokenRepository.save(
			InvalidAccessToken.builder()
				.accessTokenId(signedJWT.getJWTClaimsSet().getJWTID())
				.expiryTime(signedJWT.getJWTClaimsSet().getExpirationTime())
				.issuer(signedJWT.getJWTClaimsSet().getIssuer())
				.logoutAt(LocalDateTime.now())
				.build()
		);

	}

	@Override
	public ResponseTemplate<IntrospectResponse> authenticate(IntrospectRequest introspectRequest) throws ParseException, JOSEException {

		var accessToken = introspectRequest.getAccessToken();

		boolean verified;
		try{
			verified = jwtUtil.verifyToken(accessToken);
		} catch (AppException ignored){
			verified = false;
		}

		return ResponseTemplate.<IntrospectResponse>builder()
			.result(
				IntrospectResponse.builder()
					.isValid(verified)
					.build()
			)
			.build();
	}
}
