package com.sphere.tongthuan.UserService.service.impl;

import com.nimbusds.jose.JOSEException;
import com.sphere.tongthuan.UserService.constant.UserRole;
import com.sphere.tongthuan.UserService.dto.ResponseTemplate;
import com.sphere.tongthuan.UserService.dto.clientDto.clientDtoRequest.CreateProfileRequest;
import com.sphere.tongthuan.UserService.dto.request.LoginRequest;
import com.sphere.tongthuan.UserService.dto.request.LogoutRequest;
import com.sphere.tongthuan.UserService.dto.request.RegistrationRequest;
import com.sphere.tongthuan.UserService.dto.response.LoginResponse;
import com.sphere.tongthuan.UserService.dto.response.RegistrationResponse;
import com.sphere.tongthuan.UserService.dto.response.UserResponse;
import com.sphere.tongthuan.UserService.entity.Role;
import com.sphere.tongthuan.UserService.entity.User;
import com.sphere.tongthuan.UserService.exception.AppException;
import com.sphere.tongthuan.UserService.exception.ErrorCode;
import com.sphere.tongthuan.UserService.mapper.UserMapper;
import com.sphere.tongthuan.UserService.repository.RoleRepository;
import com.sphere.tongthuan.UserService.repository.UserRepository;
import com.sphere.tongthuan.UserService.repository.httpClient.UserProfileClient;
import com.sphere.tongthuan.UserService.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService, UserDetailsService {

    @NonFinal
    @Value("${app.token.max-refresh-token}")
    long MAX_REFRESH_TOKEN;

    private UserRepository userRepository;

    private UserMapper userMapper;

    UserProfileClient userProfileClient;

    RoleRepository roleRepository;

    @Override
    public ResponseTemplate<RegistrationResponse> register(RegistrationRequest registrationRequest) {

        if(userRepository.existsByEmail(registrationRequest.getEmail()))
            throw new AppException(ErrorCode.USER_EXISTED);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        registrationRequest.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

        Role userRole = roleRepository.findByRoleName(UserRole.USER.getRoleName()).orElseThrow(
            () -> new AppException(ErrorCode.UNAUTHENTICATED)
        );

        User user = userRepository.save(userMapper.toUserEntity(registrationRequest));

        userProfileClient.createProfile(
            CreateProfileRequest.builder()
                .userId(user.getUserId())
                .build()
        );

        return ResponseTemplate.<RegistrationResponse>builder()
            .result(RegistrationResponse.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build())
                .build();
    }

    @Override
    public ResponseTemplate<List<UserResponse>> getListOfUser() {
        return ResponseTemplate.<List<UserResponse>>builder()
            .message("Success")
            .result(
                userRepository.findAll()
                    .stream()
                    .map(userMapper::toUserResponse).toList())
            .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
