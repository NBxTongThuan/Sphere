package com.sphere.tongthuan.AuthService.util;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.sphere.tongthuan.AuthService.entity.User;
import com.sphere.tongthuan.AuthService.exception.AppException;
import com.sphere.tongthuan.AuthService.exception.ErrorCode;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.net.http.HttpRequest;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtUtil {

    @NonFinal
    @Value("${jwt.signerKey}")
    String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-access-duration}")
    public long VALID_ACCESS_DURATION;

    @NonFinal
    @Value("${jwt.valid-refresh-duration}")
    public long VALID_REFRESH_DURATION;

    public String getTokenFromCookie(HttpServletRequest httpServletRequest)
    {
        if(httpServletRequest.getCookies() != null)
        {
            for (Cookie cookie : httpServletRequest.getCookies()) {
                if ("rf_token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return "";
    }


    public SignedJWT verifyToken(String token, boolean isRefreshToken) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefreshToken)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime()
                .toInstant().plus(VALID_REFRESH_DURATION, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verify = signedJWT.verify(verifier);

        if(!(verify && expiryTime.after(new Date())))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }


    public String generateAccessToken(User user) throws JOSEException {

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet accessJwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUserId())
                .claim("email", user.getEmail())
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_ACCESS_DURATION, ChronoUnit.MINUTES).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();

        Payload accessPayload = new Payload(accessJwtClaimsSet.toJSONObject());

        JWSObject accessToken = new JWSObject(header, accessPayload);
        accessToken.sign(new MACSigner(SIGNER_KEY.getBytes()));

        return accessToken.serialize();

    }

    public String generateRefreshToken(User user) throws JOSEException {

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet refreshJwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUserId())
                .claim("type", "REFRESH")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_REFRESH_DURATION, ChronoUnit.MINUTES).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .build();

        Payload refreshPayload = new Payload(refreshJwtClaimsSet.toJSONObject());

        JWSObject refreshToken = new JWSObject(header, refreshPayload);
        refreshToken.sign(new MACSigner(SIGNER_KEY.getBytes()));

        return refreshToken.serialize();
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        if (!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(
                    role -> {
                        stringJoiner.add("ROLE_" + role.getName());
                    }
            );

        return stringJoiner.toString();
    }


}
