package com.sphere.tongthuan.UserService.configuration;

import com.sphere.tongthuan.UserService.constant.UserPermission;
import com.sphere.tongthuan.UserService.constant.UserRole;
import com.sphere.tongthuan.UserService.constant.UserStatus;
import com.sphere.tongthuan.UserService.entity.Permission;
import com.sphere.tongthuan.UserService.entity.Role;
import com.sphere.tongthuan.UserService.entity.User;
import com.sphere.tongthuan.UserService.exception.AppException;
import com.sphere.tongthuan.UserService.exception.ErrorCode;
import com.sphere.tongthuan.UserService.repository.PermissionRepository;
import com.sphere.tongthuan.UserService.repository.RoleRepository;
import com.sphere.tongthuan.UserService.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AppRunnerConfiguration {

	PasswordEncoder passwordEncoder;

	@NonFinal
	@Value("${app.account.admin.email}")
	String ADMIN_EMAIL;

	@NonFinal
	@Value("${app.account.admin.password}")
	String ADMIN_PASSWORD;

	@Bean
	@ConditionalOnProperty(
		prefix = "spring",
		value = "datasource.driverClassName",
		havingValue = "com.mysql.cj.jdbc.Driver"
	)
	ApplicationRunner applicationRunner(RoleRepository roleRepository, PermissionRepository permissionRepository,UserRepository userRepository)
	{
		log.info("Initializing application.....");
		return args ->{
			User user = userRepository.findByEmail(ADMIN_EMAIL).orElseGet(
				() ->
					userRepository.save(
						User.builder()
							.email(ADMIN_EMAIL)
							.passwordHash(passwordEncoder.encode(ADMIN_PASSWORD))
							.status(UserStatus.ACTIVE)
							.roles(new HashSet<>(
								Collections.singleton(
									roleRepository.findByRoleName(UserRole.ADMIN.getRoleName()).orElseGet(
										() -> Role.builder()
											.roleName(UserRole.ADMIN.getRoleName())
											.createdDate(LocalDateTime.now())
											.lastModified(LocalDateTime.now())
											.build()
									)
								)
							))
							.build()
					));

			buildAdminPermission(permissionRepository, roleRepository, user, userRepository);
		};
	}

	void buildAdminPermission(PermissionRepository permissionRepository, RoleRepository roleRepository, User user, UserRepository userRepository)
	{
		for(UserPermission userPermission : UserPermission.values())
			{
				permissionRepository.findByPermissionName(userPermission.getPermissionName())
					.orElseGet(() ->
						permissionRepository.save(
							Permission.builder()
								.permissionName(userPermission.getPermissionName())
								.description(userPermission.getPermissionName())
								.createdDate(LocalDateTime.now())
								.lastModified(LocalDateTime.now())
								.build()
					));
			}

		Role adminRole = roleRepository.findByRoleName(UserRole.ADMIN.getRoleName())
			.orElseThrow(
				() -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
			);
		adminRole.setPermissions(
			new HashSet<>(
				permissionRepository.findAll()
			)
		);
		roleRepository.save(adminRole);
		user.setRoles(Collections.singleton(
			adminRole
		));
		userRepository.save(user);

	}


	void MapPermissionToRole(RoleRepository roleRepository, PermissionRepository permissionRepository)
	{
		Set<Role> roles = new HashSet<>(roleRepository.findAll());
		Set<Permission> permissions = new HashSet<>(permissionRepository.findAll());
		if(roles.isEmpty() || permissions.isEmpty())
			return;

	}

}
