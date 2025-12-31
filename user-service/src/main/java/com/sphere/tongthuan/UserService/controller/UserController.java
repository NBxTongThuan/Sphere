package com.sphere.tongthuan.UserService.controller;

import com.nimbusds.jose.JOSEException;
import com.sphere.tongthuan.UserService.constant.UserPermission;
import com.sphere.tongthuan.UserService.constant.UserRole;
import com.sphere.tongthuan.UserService.dto.ResponseTemplate;
import com.sphere.tongthuan.UserService.dto.request.LoginRequest;
import com.sphere.tongthuan.UserService.dto.request.LogoutRequest;
import com.sphere.tongthuan.UserService.dto.request.RegistrationRequest;
import com.sphere.tongthuan.UserService.dto.response.LoginResponse;
import com.sphere.tongthuan.UserService.dto.response.RegistrationResponse;
import com.sphere.tongthuan.UserService.dto.response.UserResponse;
import com.sphere.tongthuan.UserService.exception.ErrorCode;
import com.sphere.tongthuan.UserService.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;


    @PostMapping("/register")
    ResponseTemplate<RegistrationResponse> register(@RequestBody @Valid RegistrationRequest registrationRequest)
    {
        return userService.register(registrationRequest);
    }

    @GetMapping("/users")
    ResponseTemplate<List<UserResponse>> getListOfUser()
    {
        String roleName = UserRole.ADMIN.getRoleName();
        return userService.getListOfUser();
    }

}
