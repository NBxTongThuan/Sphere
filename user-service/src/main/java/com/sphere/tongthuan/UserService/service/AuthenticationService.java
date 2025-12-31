package com.sphere.tongthuan.UserService.service;

import com.nimbusds.jose.JOSEException;
import com.sphere.tongthuan.UserService.dto.ResponseTemplate;
import com.sphere.tongthuan.UserService.dto.request.IntrospectRequest;
import com.sphere.tongthuan.UserService.dto.request.LoginRequest;
import com.sphere.tongthuan.UserService.dto.request.LogoutRequest;
import com.sphere.tongthuan.UserService.dto.response.IntrospectResponse;
import com.sphere.tongthuan.UserService.dto.response.LoginResponse;
import jakarta.servlet.http.HttpServletResponse;

import java.text.ParseException;

public interface AuthenticationService {

	ResponseTemplate<LoginResponse> login(LoginRequest authenticationRequest, HttpServletResponse response) throws JOSEException;
	void logout(LogoutRequest logoutRequest) throws ParseException, JOSEException;
	ResponseTemplate<IntrospectResponse> authenticate(IntrospectRequest introspectRequest) throws ParseException, JOSEException;
}
