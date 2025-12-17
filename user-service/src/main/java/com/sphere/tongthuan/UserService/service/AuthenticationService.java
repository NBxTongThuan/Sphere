package com.sphere.tongthuan.UserService.service;

import com.nimbusds.jose.JOSEException;
import com.sphere.tongthuan.UserService.dto.request.LoginRequest;
import com.sphere.tongthuan.UserService.dto.request.LogoutRequest;
import com.sphere.tongthuan.UserService.dto.response.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {
        public LoginResponse login(LoginRequest authenticationRequest, HttpServletResponse response) throws JOSEException;
        public void logout(LogoutRequest logoutRequest, HttpServletRequest request, HttpServletResponse response);
}
