package com.globaltechadvisor.SpringSecurityEx.dao;

import com.globaltechadvisor.SpringSecurityEx.model.RefreshToken;
import com.globaltechadvisor.SpringSecurityEx.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByToken(String token);

    @Modifying
    int deleteByUser(User user);
}