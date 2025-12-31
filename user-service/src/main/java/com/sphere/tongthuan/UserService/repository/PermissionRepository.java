package com.sphere.tongthuan.UserService.repository;

import com.sphere.tongthuan.UserService.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {

	Optional<Permission> findByPermissionName(String permissionName);

	@Query(
		value = """
			SELECT p FROM permission p
			INNER JOIN role_permissions rp ON p.id = rp.role_id
			INNER JOIN role r ON rp.role_id = r.id
			WHERE
			r.role_name = :roleName
			""", nativeQuery = true)
	Set<Permission> findAllByRoleName(String roleName);


}
