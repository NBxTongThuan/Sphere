package com.sphere.tongthuan.UserService.controller;

import com.nimbusds.jose.JOSEException;
import com.sphere.tongthuan.UserService.dto.ResponseTemplate;
import com.sphere.tongthuan.UserService.dto.request.LoginRequest;
import com.sphere.tongthuan.UserService.dto.request.LogoutRequest;
import com.sphere.tongthuan.UserService.dto.request.RegistrationRequest;
import com.sphere.tongthuan.UserService.dto.response.LoginResponse;
import com.sphere.tongthuan.UserService.dto.response.RegistrationResponse;
import com.sphere.tongthuan.UserService.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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
public class UserController {

    UserService userService;


    @PostMapping("/register")
    ResponseTemplate<RegistrationResponse> register(@RequestBody @Valid RegistrationRequest registrationRequest)
    {
        return userService.register(registrationRequest);
    }

    @PostMapping("/login")
    ResponseTemplate<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) throws JOSEException {
        return userService.login(loginRequest, response);
    }

    @PostMapping("/logout")
    ResponseTemplate<String> logout(@RequestBody LogoutRequest logoutRequest, HttpServletRequest httpServletRequest, HttpServletResponse response) throws JOSEException {
        userService.logout(logoutRequest, httpServletRequest, response);
        return ResponseTemplate.<String>builder().result("success").code(1000).build();
    }


}
