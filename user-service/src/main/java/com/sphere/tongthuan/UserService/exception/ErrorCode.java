package com.sphere.tongthuan.UserService.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@AllArgsConstructor
@Getter
public enum ErrorCode {

	UNCATEGORIZED_EXCEPTION(9999,"Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
	INVALID_KEY(9998,"Invalid message key", HttpStatus.BAD_REQUEST),
	USER_EXISTED(1001,"User existed!", HttpStatus.BAD_REQUEST),
	INVALID_EMAIL(1002,"Email is not valid!", HttpStatus.BAD_REQUEST),
	INVALID_DOB(1005,"Min dob is {min}!", HttpStatus.BAD_REQUEST),
	INVALID_PASSWORD(1003,"Password must be at least 8 characters!", HttpStatus.BAD_REQUEST),
	UNAUTHORIZED(9997,"You do not have permission!", HttpStatus.FORBIDDEN),
	UNAUTHENTICATED(9996,"Unauthenticated!", HttpStatus.UNAUTHORIZED),
	;
	private final int code;
	private final String message;
	private final HttpStatusCode httpStatusCode;

}
