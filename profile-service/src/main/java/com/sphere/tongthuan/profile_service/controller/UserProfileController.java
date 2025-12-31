package com.sphere.tongthuan.profile_service.controller;

import com.sphere.tongthuan.constant.AppMessage;
import com.sphere.tongthuan.dto.BaseResponse;
import com.sphere.tongthuan.profile_service.dto.request.CreateProfileRequest;
import com.sphere.tongthuan.profile_service.dto.response.UserProfileResponse;
import com.sphere.tongthuan.profile_service.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-profile")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@EnableMethodSecurity
public class UserProfileController {

    private UserProfileService userProfileService;

    @PostMapping("/create")
    private BaseResponse<UserProfileResponse> create(@RequestBody CreateProfileRequest createProfileRequest)
    {
        return BaseResponse.<UserProfileResponse>builder()
            .result(userProfileService.createUserProfile(createProfileRequest))
            .message(AppMessage.A0001)
            .code(HttpStatus.CREATED.value())
            .build();
    }

}
