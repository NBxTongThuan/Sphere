package com.sphere.tongthuan.UserService.exception;

import com.sphere.tongthuan.UserService.dto.ResponseTemplate;
import jakarta.validation.ConstraintViolation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String MIN_ATTRIBUTE = "min";

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ResponseTemplate<?>> handlingRuntimeException(RuntimeException exception){

        return ResponseEntity.badRequest().body(
            ResponseTemplate.builder()
                .code(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode())
                .message(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage())
                .build());
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ResponseTemplate<?>> handlingAppException(AppException exception){

        ErrorCode errorCode = exception.getErrorCode();

        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(
            ResponseTemplate.builder()
                .message(errorCode.getMessage())
                .code(errorCode.getCode())
                .build());

    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ResponseTemplate<?>> handlingAccessDeniedException(AccessDeniedException accessDeniedException)
    {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(
            ResponseTemplate.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build()
        );
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ResponseTemplate<?>> handlingMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

		ErrorCode errorCode = ErrorCode.INVALID_KEY;
		Map<String, Object> attributes = new HashMap<>();
		try {
			errorCode = ErrorCode.valueOf(Objects.requireNonNull(exception.getFieldError()).getDefaultMessage());

			var constraintViolation = exception.getBindingResult()
				.getAllErrors().getFirst().unwrap(ConstraintViolation.class);

			attributes = constraintViolation.getConstraintDescriptor().getAttributes();

		} catch (Exception ignored) {
		}

		return ResponseEntity.badRequest().body(
			ResponseTemplate.builder()
				.code(errorCode.getCode())
				.message(mapAttribute(errorCode.getMessage(), attributes))
				.build()
		);

	}

    private String mapAttribute(String message, Map<String, Object> attributes){

        String minValue = attributes.get(MIN_ATTRIBUTE).toString();

        return message.replace("{"+MIN_ATTRIBUTE+"}", minValue);

    }

}
