package com.sphere.tongthuan.profile_service.service;

import com.sphere.tongthuan.profile_service.dto.request.CreateProfileRequest;
import com.sphere.tongthuan.profile_service.dto.response.UserProfileResponse;

public interface UserProfileService {

    UserProfileResponse createUserProfile(CreateProfileRequest createProfileRequest);

}
