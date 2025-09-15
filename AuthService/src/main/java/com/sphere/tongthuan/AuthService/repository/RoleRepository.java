package com.sphere.tongthuan.AuthService.repository;

import com.sphere.tongthuan.AuthService.entity.Role;
import com.sphere.tongthuan.constant.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Roles> {

}
