package com.sphere.tongthuan.UserService.exception;

import com.sphere.tongthuan.UserService.dto.ResponseTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
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

        return ResponseEntity.badRequest().body(
            ResponseTemplate.builder()
                .message(errorCode.getMessage())
                .code(errorCode.getCode())
                .build());

    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ResponseTemplate<?>> handlingMethodArgumentNotValidException(MethodArgumentNotValidException exception){

        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        try{
            errorCode = ErrorCode.valueOf(Objects.requireNonNull(exception.getFieldError()).getDefaultMessage());
        }catch (Exception ignored)
        {}

		return ResponseEntity.badRequest().body(
            ResponseTemplate.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build()
        );

    }

}
