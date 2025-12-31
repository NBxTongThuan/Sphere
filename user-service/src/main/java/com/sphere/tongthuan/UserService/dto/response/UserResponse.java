package com.sphere.tongthuan.UserService.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sphere.tongthuan.UserService.constant.UserStatus;
import com.sphere.tongthuan.UserService.entity.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

	String userId;
	String email;
	String lastName;
	String firstName;
	UserStatus status = UserStatus.INACTIVE;
	LocalDateTime createdAt = LocalDateTime.now();
	LocalDateTime updatedAt = LocalDateTime.now();
	LocalDateTime lastLogin;
	Set<Role> roles;

}
