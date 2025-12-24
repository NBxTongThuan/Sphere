package com.sphere.tongthuan.UserService.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationRequest {

	@Email(message = "INVALID_EMAIL")
	private String email;

	@Size(min = 8, message = "INVALID_PASSWORD")
	private String password;

	private String firstName;

	private String lastName;
}
