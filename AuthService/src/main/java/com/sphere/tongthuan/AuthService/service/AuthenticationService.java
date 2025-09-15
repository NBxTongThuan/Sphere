package com.sphere.tongthuan.AuthService.service;

import com.nimbusds.jose.JOSEException;
import com.sphere.tongthuan.AuthService.dto.request.LoginRequest;
import com.sphere.tongthuan.AuthService.dto.request.LogoutRequest;
import com.sphere.tongthuan.AuthService.dto.response.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

public interface AuthenticationService {
        public LoginResponse login(LoginRequest authenticationRequest, HttpServletResponse response) throws JOSEException;
        public void logout(LogoutRequest logoutRequest, HttpServletRequest request, HttpServletResponse response);
}
