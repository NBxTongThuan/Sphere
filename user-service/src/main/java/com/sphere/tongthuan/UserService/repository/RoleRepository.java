package com.sphere.tongthuan.UserService.repository;

import com.sphere.tongthuan.UserService.entity.Role;
import com.sphere.tongthuan.constant.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Roles> {

}
