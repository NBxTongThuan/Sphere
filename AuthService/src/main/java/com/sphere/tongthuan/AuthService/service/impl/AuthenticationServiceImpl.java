package com.sphere.tongthuan.AuthService.service.impl;

import com.nimbusds.jose.JOSEException;
import com.sphere.tongthuan.AuthService.dto.request.LoginRequest;
import com.sphere.tongthuan.AuthService.dto.request.LogoutRequest;
import com.sphere.tongthuan.AuthService.dto.response.LoginResponse;
import com.sphere.tongthuan.AuthService.entity.RefreshToken;
import com.sphere.tongthuan.AuthService.exception.AppException;
import com.sphere.tongthuan.AuthService.exception.ErrorCode;
import com.sphere.tongthuan.AuthService.repository.RefreshTokenRepository;
import com.sphere.tongthuan.AuthService.repository.UserRepository;
import com.sphere.tongthuan.AuthService.service.AuthenticationService;
import com.sphere.tongthuan.AuthService.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements AuthenticationService {

    RefreshTokenRepository refreshTokenRepository;

    JwtUtil jwtUtil;

    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${app.token.max-refresh-token}")
    long MAX_REFRESH_TOKEN;

    @Override
    public LoginResponse login(LoginRequest loginRequest, HttpServletResponse response) throws JOSEException {

        var user = userRepository
                .findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        boolean authenticated = passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash());

        if (!authenticated) throw new AppException(ErrorCode.UNAUTHENTICATED);

        List<RefreshToken> refreshTokens = refreshTokenRepository.findAllByUser(user);

        RefreshToken refreshToken;
        if (refreshTokens.size() < 5) {
            refreshToken = RefreshToken.builder()
                    .createdAt(LocalDateTime.now())
                    .expiryDate(LocalDateTime.now().plusDays(7))
                    .user(user)
                    .token(jwtUtil.generateRefreshToken(user))
                    .build();

            refreshTokenRepository.save(refreshToken);

        } else {
            refreshToken = refreshTokens.stream().min(Comparator.comparing(RefreshToken::getExpiryDate)).orElseThrow(() -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION));

            if (refreshToken != null) {
                refreshTokenRepository.delete(refreshToken);
            }
            refreshToken = RefreshToken.builder()
                    .createdAt(LocalDateTime.now())
                    .expiryDate(LocalDateTime.now().plusDays(7))
                    .user(user)
                    .token(jwtUtil.generateRefreshToken(user))
                    .build();
            refreshTokenRepository.save(refreshToken);
        }
        String accessToken = jwtUtil.generateAccessToken(user);

        Cookie cookie = new Cookie("rf_token", refreshToken.getToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // nếu dev local thì có thể tạm để false
        cookie.setPath("/");
        cookie.setMaxAge(Instant.ofEpochMilli(jwtUtil.VALID_REFRESH_DURATION).getNano());
        response.addCookie(cookie);

        return LoginResponse.builder()
                .token(accessToken)
                .build();
    }

    @Override
    public void logout(LogoutRequest logoutRequest, HttpServletRequest request, HttpServletResponse response
                       ) {
        String rfToken = jwtUtil.getTokenFromCookie(request);

        RefreshToken refreshToken = refreshTokenRepository.findByToken(rfToken).orElseThrow(() -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION));
        if(refreshToken!=null)
        {
            refreshTokenRepository.delete(refreshToken);
        }
        Cookie cookie = new Cookie("rf_token", "");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // Xóa cookie
        response.addCookie(cookie);
    }
}
