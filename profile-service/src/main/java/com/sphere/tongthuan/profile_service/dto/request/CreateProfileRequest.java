package com.sphere.tongthuan.profile_service.dto.request;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProfileRequest {

    private LocalDate dateOfBirth;

    private LocalDateTime createdDate;

    private LocalDateTime lastModified;

    private String address;

    private String phoneNumber;

    private String userId;

}
