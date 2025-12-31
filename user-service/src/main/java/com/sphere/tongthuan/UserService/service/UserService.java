package com.sphere.tongthuan.UserService.service;

import com.nimbusds.jose.JOSEException;
import com.sphere.tongthuan.UserService.dto.ResponseTemplate;
import com.sphere.tongthuan.UserService.dto.request.LoginRequest;
import com.sphere.tongthuan.UserService.dto.request.LogoutRequest;
import com.sphere.tongthuan.UserService.dto.request.RegistrationRequest;
import com.sphere.tongthuan.UserService.dto.response.LoginResponse;
import com.sphere.tongthuan.UserService.dto.response.RegistrationResponse;
import com.sphere.tongthuan.UserService.dto.response.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface UserService {

    ResponseTemplate<RegistrationResponse> register(RegistrationRequest registrationRequest);
    ResponseTemplate<List<UserResponse>> getListOfUser();

}
