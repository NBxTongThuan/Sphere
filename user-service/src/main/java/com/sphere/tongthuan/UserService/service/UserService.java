package com.sphere.tongthuan.UserService.service;

import com.nimbusds.jose.JOSEException;
import com.sphere.tongthuan.UserService.dto.ResponseTemplate;
import com.sphere.tongthuan.UserService.dto.request.LoginRequest;
import com.sphere.tongthuan.UserService.dto.request.LogoutRequest;
import com.sphere.tongthuan.UserService.dto.request.RegistrationRequest;
import com.sphere.tongthuan.UserService.dto.response.LoginResponse;
import com.sphere.tongthuan.UserService.dto.response.RegistrationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    ResponseTemplate<LoginResponse> login(LoginRequest authenticationRequest, HttpServletResponse response) throws JOSEException;
    void logout(LogoutRequest logoutRequest, HttpServletRequest request, HttpServletResponse response);
    ResponseTemplate<RegistrationResponse> register(RegistrationRequest registrationRequest);
}
