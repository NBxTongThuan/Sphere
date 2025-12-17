package com.sphere.tongthuan.UserService.controller;

import com.nimbusds.jose.JOSEException;
import com.sphere.tongthuan.UserService.dto.request.LoginRequest;
import com.sphere.tongthuan.UserService.dto.request.LogoutRequest;
import com.sphere.tongthuan.UserService.dto.response.LoginResponse;
import com.sphere.tongthuan.UserService.service.AuthenticationService;
import com.sphere.tongthuan.dto.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/login")
    BaseResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) throws JOSEException {
        var result = authenticationService.login(loginRequest, response);
        return BaseResponse.<LoginResponse>builder().result(result).build();
    }

    @PostMapping("/logout")
    BaseResponse<String> logout(@RequestBody LogoutRequest logoutRequest, HttpServletRequest httpServletRequest, HttpServletResponse response) throws JOSEException {
        authenticationService.logout(logoutRequest, httpServletRequest, response);
        return BaseResponse.<String>builder().result("success").code(1000).build();
    }


}
