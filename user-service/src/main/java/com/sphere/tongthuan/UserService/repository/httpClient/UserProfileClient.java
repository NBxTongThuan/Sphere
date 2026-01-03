package com.sphere.tongthuan.UserService.repository.httpClient;

import com.sphere.tongthuan.UserService.dto.clientDto.clientDtoRequest.CreateProfileRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "profile-service", url = "http://localhost:8081/profile-service/internal")
public interface UserProfileClient {

	@PostMapping(value = "/user-profile/create", produces = MediaType.APPLICATION_JSON_VALUE)
	Object createProfile(@RequestBody CreateProfileRequest createProfileRequest);

}
