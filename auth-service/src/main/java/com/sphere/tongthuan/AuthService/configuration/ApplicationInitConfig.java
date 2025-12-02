package com.sphere.tongthuan.AuthService.configuration;

import com.sphere.tongthuan.AuthService.constant.UserStatus;
import com.sphere.tongthuan.AuthService.entity.Role;
import com.sphere.tongthuan.AuthService.entity.User;
import com.sphere.tongthuan.AuthService.repository.RoleRepository;
import com.sphere.tongthuan.AuthService.repository.UserRepository;
import com.sphere.tongthuan.constant.Roles;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @Value("${app.account.admin.email}")
    @NonFinal
    private String ADMIN_EMAIL;

    @Value("${app.account.admin.password}")
    @NonFinal
    private String ADMIN_PASSWORD;

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.jdbc.Driver"
    )
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository)
    {
        log.info("Initializing application.....");
        return args -> {
            if (userRepository.findByEmail(ADMIN_EMAIL).isEmpty()) {
                roleRepository.save(Role.builder()
                        .roleName(Roles.USER.name())
                        .description("User role")
                        .build());

                Role adminRole = roleRepository.save(Role.builder()
                        .roleName(Roles.ADMIN.name())
                        .description("Admin role")
                        .build());

                var roles = new HashSet<Role>();
                roles.add(adminRole);

                User user = User.builder()
                        .email(ADMIN_EMAIL)
                        .status(UserStatus.ACTIVE)
                        .updatedAt(LocalDateTime.now())
                        .passwordHash(passwordEncoder.encode(ADMIN_PASSWORD))
                        .roles(roles)
                        .build();

                userRepository.save(user);
                log.warn("admin user has been created with default password: admin, please change it");
            }
            log.info("Application initialization completed .....");
        };
    }



}
