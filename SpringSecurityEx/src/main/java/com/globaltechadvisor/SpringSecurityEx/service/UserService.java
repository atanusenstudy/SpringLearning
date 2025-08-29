package com.globaltechadvisor.SpringSecurityEx.service;

import com.globaltechadvisor.SpringSecurityEx.dao.UserRepo;
import com.globaltechadvisor.SpringSecurityEx.dto.TokenResponse;
import com.globaltechadvisor.SpringSecurityEx.model.RefreshToken;
import com.globaltechadvisor.SpringSecurityEx.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    //Using the object to verify
    @Autowired
    private AuthenticationManager authenticationManager;

    //For JWTService
    @Autowired
    private JWTService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    //BCrypt for hashing 2^n times and its from spring security library
    private BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder(12);

    public User register(User user){
        //Encrypting password
        user.setPassword(passEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public List<User> users(){
        return userRepo.findAll();
    }

    //getting a box with token
    public TokenResponse verify(User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if (authentication.isAuthenticated()) {
            User authenticatedUser = userRepo.findByUsername(user.getUsername());
            String token = jwtService.generateToken(user.getUsername(), authenticatedUser.getRole().name());
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authenticatedUser.getId());

            return new TokenResponse(token, refreshToken.getToken());
        }

        throw new RuntimeException("Invalid credentials");
    }

    public TokenResponse refreshToken(String refreshToken) {
        return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtService.generateToken(user.getUsername(), user.getRole().name());
                    return new TokenResponse(token, refreshToken);
                })
                .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }
}
