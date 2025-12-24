package com.sphere.tongthuan.UserService.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

	UNCATEGORIZED_EXCEPTION(9999,"Uncategorized error"),
	INVALID_KEY(9998,"Invalid message key"),
	USER_EXISTED(1001,"User existed!"),
	INVALID_EMAIL(1002,"Email is not valid!"),
	INVALID_PASSWORD(1003,"Password must be at least 8 characters!"),

	;
	private int code;
	private String message;

}
