package com.sphere.tongthuan.UserService.mapper;

import com.sphere.tongthuan.UserService.dto.request.RegistrationRequest;
import com.sphere.tongthuan.UserService.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

	public User toUserEntity(RegistrationRequest registrationRequest)
	{
		return User.builder()
			.email(registrationRequest.getEmail())
			.passwordHash(registrationRequest.getPassword())
			.firstName(registrationRequest.getFirstName())
			.lastName(registrationRequest.getLastName())
			.build();
	}

	User toRegistrationResponse(RegistrationRequest registrationRequest)
	{
		return User.builder()
			.email(registrationRequest.getEmail())
			.passwordHash(registrationRequest.getPassword())
			.build();
	}


}
