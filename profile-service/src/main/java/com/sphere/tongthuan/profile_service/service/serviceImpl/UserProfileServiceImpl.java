package com.sphere.tongthuan.profile_service.service.serviceImpl;

import com.sphere.tongthuan.profile_service.dto.request.CreateProfileRequest;
import com.sphere.tongthuan.profile_service.dto.response.UserProfileResponse;
import com.sphere.tongthuan.profile_service.entity.UserProfile;
import com.sphere.tongthuan.profile_service.mapper.UserProfileMapper;
import com.sphere.tongthuan.profile_service.repository.UserProfileRepository;
import com.sphere.tongthuan.profile_service.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileServiceImpl implements UserProfileService {

    private UserProfileRepository userProfileRepository;
    private UserProfileMapper userProfileMapper;

    @Override
    public UserProfileResponse createUserProfile(CreateProfileRequest createProfileRequest) {

        UserProfile userProfile = userProfileRepository.save(
            userProfileMapper.toUserProfile(createProfileRequest)
        );
        return userProfileMapper.toUserProfileResponse(userProfile);

    }
}
