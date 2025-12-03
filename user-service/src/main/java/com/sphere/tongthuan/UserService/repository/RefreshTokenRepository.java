package com.sphere.tongthuan.UserService.repository;

import com.sphere.tongthuan.UserService.entity.RefreshToken;
import com.sphere.tongthuan.UserService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    List<RefreshToken> findAllByUser(User user);

    Optional<RefreshToken> findByToken(String refreshToken);

}
