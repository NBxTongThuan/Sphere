package com.sphere.tongthuan.UserService.controller;

import com.nimbusds.jose.JOSEException;
import com.sphere.tongthuan.UserService.dto.ResponseTemplate;
import com.sphere.tongthuan.UserService.dto.request.IntrospectRequest;
import com.sphere.tongthuan.UserService.dto.request.LoginRequest;
import com.sphere.tongthuan.UserService.dto.request.LogoutRequest;
import com.sphere.tongthuan.UserService.dto.response.IntrospectResponse;
import com.sphere.tongthuan.UserService.dto.response.LoginResponse;
import com.sphere.tongthuan.UserService.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

	AuthenticationService authenticationService;

	@PostMapping("/login")
	ResponseTemplate<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) throws JOSEException {
		return authenticationService.login(loginRequest, response);
	}

	@PostMapping("/logout")
	ResponseTemplate<String> logout(@RequestBody LogoutRequest logoutRequest) throws JOSEException, ParseException {
		authenticationService.logout(logoutRequest);
		return ResponseTemplate.<String>builder().result("success").code(1000).build();
	}

	@PostMapping("/introspect")
	ResponseTemplate<IntrospectResponse> authenticate(@RequestBody IntrospectRequest introspectRequest) throws JOSEException, ParseException {
		return authenticationService.authenticate(introspectRequest);
	}


}
