package com.sphere.tongthuan.UserService.repository;

import com.sphere.tongthuan.UserService.entity.InvalidAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidAccessTokenRepository extends JpaRepository<InvalidAccessToken, String> {
	
}
