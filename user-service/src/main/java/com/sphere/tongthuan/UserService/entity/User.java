package com.sphere.tongthuan.UserService.entity;


import com.sphere.tongthuan.UserService.constant.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    String userId;

    @Column(nullable = false, unique = true, length = 100)
    String email;

    @Column(nullable = false, unique = true, length = 100)
    String lastName;

    @Column(nullable = false, unique = true, length = 100)
    String firstName;

    @Column(nullable = false, length = 255)
    String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    UserStatus status = UserStatus.INACTIVE;

    LocalDateTime createdAt = LocalDateTime.now();

    LocalDateTime updatedAt = LocalDateTime.now();

    LocalDateTime lastLogin;

    @ManyToMany
    Set<Role> roles;

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL})
    List<RefreshToken> refreshTokens;

}
