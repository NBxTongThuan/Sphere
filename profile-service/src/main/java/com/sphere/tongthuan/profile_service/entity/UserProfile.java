package com.sphere.tongthuan.profile_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String profileId;

    private LocalDate dateOfBirth;

    private LocalDateTime createdDate;

    private LocalDateTime lastModified;

    private String address;

    @Column(length = 10)
    private String phoneNumber;

    @Column(unique = true, nullable = false)
    private String userId;

}
