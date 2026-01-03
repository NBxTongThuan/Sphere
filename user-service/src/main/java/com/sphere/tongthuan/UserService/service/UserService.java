package com.sphere.tongthuan.UserService.service;


import com.sphere.tongthuan.UserService.dto.ResponseTemplate;
import com.sphere.tongthuan.UserService.dto.request.RegistrationRequest;
import com.sphere.tongthuan.UserService.dto.response.RegistrationResponse;
import com.sphere.tongthuan.UserService.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    ResponseTemplate<RegistrationResponse> register(RegistrationRequest registrationRequest);
    ResponseTemplate<List<UserResponse>> getListOfUser();

}
