package com.sphere.tongthuan.UserService.util;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.sphere.tongthuan.UserService.entity.User;
import com.sphere.tongthuan.UserService.exception.AppException;
import com.sphere.tongthuan.UserService.exception.ErrorCode;
import com.sphere.tongthuan.UserService.repository.InvalidAccessTokenRepository;
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

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
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

    InvalidAccessTokenRepository invalidAccessTokenRepository;

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


    public Boolean verifyToken(String token)
        throws JOSEException, ParseException {

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        if (invalidAccessTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        var verified = signedJWT.verify(verifier);

        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        return verified && expirationTime.after(new Date());
    }


    public String  generateAccessToken(User user) throws JOSEException {

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
            .jwtID(String.valueOf(UUID.randomUUID()))
            .subject(user.getEmail())
            .issuer("TongThuan")
            .issueTime(new Date())
            .expirationTime(
                new Date(Instant.now().plus(VALID_ACCESS_DURATION, ChronoUnit.MINUTES).toEpochMilli())
            )
            .claim("scope",buildScope(user))
            .claim("type", "ACCESS")
            .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

       JWSObject jwsObject = new JWSObject(header, payload);

       jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));

       return jwsObject.serialize();
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
                        stringJoiner.add("ROLE_"+role.getRoleName());
                        role.getPermissions().forEach(
                            permission ->
                            {
                                stringJoiner.add(permission.getPermissionName());
                            }
                        );

                    }
            );

        return stringJoiner.toString();
    }
}
