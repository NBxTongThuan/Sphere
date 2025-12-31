package com.sphere.tongthuan.profile_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseTemplate<T> {
    @Builder.Default
    int code = 1000;
    @Builder.Default
    String message = "Success";
    @Builder.Default
    long timestamp = LocalDateTime.now().atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
    T result;
}
