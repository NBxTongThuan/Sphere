package com.sphere.tongthuan.UserService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "invalid_access_token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvalidAccessToken {

	@Id
	String accessTokenId;

	String issuer;

	LocalDateTime logoutAt;

	Date expiryTime;
}
