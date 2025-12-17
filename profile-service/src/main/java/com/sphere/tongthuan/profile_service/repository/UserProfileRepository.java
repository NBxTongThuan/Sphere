package com.sphere.tongthuan.profile_service.repository;

import com.sphere.tongthuan.profile_service.dto.request.CreateProfileRequest;
import com.sphere.tongthuan.profile_service.dto.response.UserProfileResponse;
import com.sphere.tongthuan.profile_service.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile,String> {

}
