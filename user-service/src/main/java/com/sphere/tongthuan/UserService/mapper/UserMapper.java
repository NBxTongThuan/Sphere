package com.sphere.tongthuan.UserService.mapper;

import com.sphere.tongthuan.UserService.constant.UserRole;
import com.sphere.tongthuan.UserService.dto.request.RegistrationRequest;
import com.sphere.tongthuan.UserService.dto.response.UserResponse;
import com.sphere.tongthuan.UserService.entity.User;
import com.sphere.tongthuan.UserService.exception.AppException;
import com.sphere.tongthuan.UserService.exception.ErrorCode;
import com.sphere.tongthuan.UserService.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class UserMapper {

	private RoleRepository roleRepository;

	public User toUserEntity(RegistrationRequest registrationRequest)
	{
		return User.builder()
			.email(registrationRequest.getEmail())
			.passwordHash(registrationRequest.getPassword())
			.roles(Collections.singleton(
				roleRepository.findByRoleName(UserRole.USER.getRoleName()).orElseThrow(
					() -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
				)
			))
			.firstName(registrationRequest.getFirstName())
			.lastName(registrationRequest.getLastName())
			.build();
	}

	public User toRegistrationResponse(RegistrationRequest registrationRequest)
	{
		return User.builder()
			.email(registrationRequest.getEmail())
			.build();
	}

	public UserResponse toUserResponse(User user)
	{
		return UserResponse.builder()
			.userId(user.getUserId())
			.email(user.getEmail())
			.lastLogin(user.getLastLogin())
			.createdAt(user.getCreatedAt())
			.roles(user.getRoles())
			.status(user.getStatus())
			.updatedAt(user.getUpdatedAt())
			.build();
	}


}
