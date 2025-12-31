package com.sphere.tongthuan.profile_service.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {

    private String profileId;

    private LocalDate dateOfBirth;

    private LocalDateTime createdDate;

    private LocalDateTime lastModified;

    private String address;

    private String phoneNumber;

    private String userId;

}
