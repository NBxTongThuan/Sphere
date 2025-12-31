package com.sphere.tongthuan.UserService.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {

	USER("USER", "Regular user with basic social network access"),
	MODERATOR("MODERATOR", "Moderator who reviews content and handles reports"),
	ADMIN("ADMIN", "Administrator with full system access");

	private String roleName;
	private String description;
}
