package com.sphere.tongthuan.UserService.mapper;

import com.sphere.tongthuan.UserService.constant.UserRole;
import com.sphere.tongthuan.UserService.constant.UserStatus;
import com.sphere.tongthuan.UserService.dto.request.RegistrationRequest;
import com.sphere.tongthuan.UserService.dto.response.UserResponse;
import com.sphere.tongthuan.UserService.entity.Role;
import com.sphere.tongthuan.UserService.entity.User;
import com.sphere.tongthuan.UserService.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class UserMapper {

	private final RoleRepository roleRepository;

	public User toUserEntity(RegistrationRequest registrationRequest)
	{
		return User.builder()
			.email(registrationRequest.getEmail())
			.passwordHash(registrationRequest.getPassword())
			.status(UserStatus.INACTIVE)
			.roles(new HashSet<>(
				Collections.singleton(
					roleRepository.findByRoleName(UserRole.USER.getRoleName()).orElseGet(
						() -> roleRepository.save(
							Role.builder()
								.roleName(UserRole.USER.getRoleName())
								.createdDate(LocalDateTime.now())
								.lastModified(LocalDateTime.now())
								.build()
						)
					)
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
