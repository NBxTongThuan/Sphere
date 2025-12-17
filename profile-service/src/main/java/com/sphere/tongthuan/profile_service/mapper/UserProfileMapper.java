package com.sphere.tongthuan.profile_service.mapper;


import com.sphere.tongthuan.profile_service.dto.request.CreateProfileRequest;
import com.sphere.tongthuan.profile_service.dto.response.UserProfileResponse;
import com.sphere.tongthuan.profile_service.entity.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    UserProfile toUserProfile(CreateProfileRequest createProfileRequest);

    UserProfileResponse toUserProfileResponse(UserProfile profile);

}
