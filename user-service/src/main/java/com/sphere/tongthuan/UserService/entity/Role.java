package com.sphere.tongthuan.UserService.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = true)
    private String roleName;

    private String description;

    private LocalDateTime createdDate;

    private LocalDateTime lastModified;

    @ManyToMany
    private Set<Permission> permissions;

}
