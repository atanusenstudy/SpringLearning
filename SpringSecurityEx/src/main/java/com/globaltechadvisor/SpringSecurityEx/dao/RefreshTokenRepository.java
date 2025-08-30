package com.globaltechadvisor.SpringSecurityEx.dao;

import com.globaltechadvisor.SpringSecurityEx.model.RefreshToken;
import com.globaltechadvisor.SpringSecurityEx.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
    Each user can only have one refresh token at a time
    When a user logs in again, their existing refresh token is updated instead of creating a new one
    The unique constraint on user_id is respected
*/
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUser_Id(Integer userId);

    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.user = :user")
    int deleteByUser(@Param("user") User user);

    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.token = :token")
    void deleteByToken(@Param("token") String token);
}