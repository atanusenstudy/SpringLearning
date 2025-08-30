package com.globaltechadvisor.SpringSecurityEx.service;

import com.globaltechadvisor.SpringSecurityEx.dao.UserRepo;
import com.globaltechadvisor.SpringSecurityEx.dto.TokenResponse;
import com.globaltechadvisor.SpringSecurityEx.dto.UserUpdateRequest;
import com.globaltechadvisor.SpringSecurityEx.model.RefreshToken;
import com.globaltechadvisor.SpringSecurityEx.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.globaltechadvisor.SpringSecurityEx.dto.UserDTO;
import com.globaltechadvisor.SpringSecurityEx.model.User.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;
import java.util.stream.Collectors;

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

    //UserManagement
    public List<UserDTO> getAllUsers() {
        return userRepo.findAll().stream()
                .map(UserDTO::fromUser)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Integer id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserDTO.fromUser(user);
    }

    public UserDTO updateUser(Integer id, User updatedUser, String currentUsername) {
        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Get current user's authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        // Check if current user is admin or updating their own profile
        if (!isAdmin && !existingUser.getUsername().equals(currentUsername)) {
            throw new RuntimeException("You can only update your own profile");
        }

        // Only admin can change roles
        if (updatedUser.getRole() != null && !isAdmin) {
            throw new RuntimeException("Only admin can change roles");
        }

        if (updatedUser.getUsername() != null) {
            existingUser.setUsername(updatedUser.getUsername());
        }

        if (updatedUser.getPassword() != null) {
            existingUser.setPassword(passEncoder.encode(updatedUser.getPassword()));
        }

        if (updatedUser.getRole() != null && isAdmin) {
            existingUser.setRole(updatedUser.getRole());
        }

        User savedUser = userRepo.save(existingUser);
        return UserDTO.fromUser(savedUser);
    }

    public void deleteUser(Integer id, String currentUsername) {
        User userToDelete = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Get current user's authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        // Check if current user is admin or deleting their own account
        if (!isAdmin && !userToDelete.getUsername().equals(currentUsername)) {
            throw new RuntimeException("You can only delete your own account");
        }

        // Prevent admin from deleting themselves if they're the only admin
        if (isAdmin && userToDelete.getUsername().equals(currentUsername)) {
            long adminCount = userRepo.countByRole(Role.ADMIN);
            if (adminCount <= 1) {
                throw new RuntimeException("Cannot delete the only admin account");
            }
        }

        // Delete refresh tokens first
        refreshTokenService.deleteByUserId(id);
        userRepo.deleteById(id);
    }

    public UserDTO updateCurrentUser(Integer userId, UserUpdateRequest updateRequest) {
        User existingUser = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (updateRequest.getUsername() != null && !updateRequest.getUsername().isEmpty()) {
            // Check if username is already taken by another user
            User userWithSameUsername = userRepo.findByUsername(updateRequest.getUsername());
            if (userWithSameUsername != null && !userWithSameUsername.getId().equals(userId)) {
                throw new RuntimeException("Username already taken");
            }
            existingUser.setUsername(updateRequest.getUsername());
        }

        if (updateRequest.getPassword() != null && !updateRequest.getPassword().isEmpty()) {
            existingUser.setPassword(passEncoder.encode(updateRequest.getPassword()));
        }

        User savedUser = userRepo.save(existingUser);
        return UserDTO.fromUser(savedUser);
    }
}
