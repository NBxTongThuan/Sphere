package com.sphere.tongthuan.AuthService.entity;


import com.sphere.tongthuan.constant.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @Column(nullable = false, unique = true, length = 50)
    private String name;

    private String description;

}
