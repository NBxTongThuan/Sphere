package com.sphere.tongthuan.UserService.controller.internalUserServiceController;

import com.nimbusds.jose.JOSEException;
import com.sphere.tongthuan.UserService.dto.ResponseTemplate;
import com.sphere.tongthuan.UserService.dto.request.IntrospectRequest;
import com.sphere.tongthuan.UserService.dto.request.LoginRequest;
import com.sphere.tongthuan.UserService.dto.request.LogoutRequest;
import com.sphere.tongthuan.UserService.dto.response.IntrospectResponse;
import com.sphere.tongthuan.UserService.dto.response.LoginResponse;
import com.sphere.tongthuan.UserService.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/internal/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InternalAuthenticationController {

	AuthenticationService authenticationService;

	@PostMapping("/introspect")
	ResponseTemplate<IntrospectResponse> authenticate(@RequestBody IntrospectRequest introspectRequest) throws JOSEException, ParseException {
		return authenticationService.authenticate(introspectRequest);
	}

}
