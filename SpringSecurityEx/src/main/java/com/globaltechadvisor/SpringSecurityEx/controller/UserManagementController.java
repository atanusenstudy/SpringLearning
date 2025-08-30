package com.globaltechadvisor.SpringSecurityEx.controller;

// ... existing imports ...
import com.globaltechadvisor.SpringSecurityEx.dao.UserRepo;
import com.globaltechadvisor.SpringSecurityEx.dto.UserDTO;
import com.globaltechadvisor.SpringSecurityEx.dto.UserUpdateRequest;
import com.globaltechadvisor.SpringSecurityEx.model.User;
import com.globaltechadvisor.SpringSecurityEx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserManagementController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    // Get current user's profile
    @GetMapping("/profile")
    public UserDTO getCurrentUserProfile(Authentication authentication) {
        User currentUser = userRepo.findByUsername(authentication.getName());
        return UserDTO.fromUser(currentUser);
    }

    // Update current user's profile
    // Can update only some part as well as whole object:
    //  {    "password": "newpassword123" }
    //  {  "username": "newusername" }
    @PutMapping("/profile")
    public UserDTO updateCurrentUserProfile(@RequestBody UserUpdateRequest updateRequest,
                                            Authentication authentication) {
        User currentUser = userRepo.findByUsername(authentication.getName());
        return userService.updateCurrentUser(currentUser.getId(), updateRequest);
    }

    // Get all users - only accessible by ADMIN
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get user by ID - accessible by the user themselves or ADMIN
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Integer id, Authentication authentication) {
        UserDTO user = userService.getUserById(id);

        // Check if the current user is the requested user or an admin
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !authentication.getName().equals(user.getUsername())) {
            throw new RuntimeException("Access denied");
        }

        return user;
    }

    // Update user - accessible by the user themselves or ADMIN
    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Integer id, @RequestBody User updatedUser, Authentication authentication) {
        return userService.updateUser(id, updatedUser, authentication.getName());
    }

    // Delete user - accessible by the user themselves or ADMIN
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id, Authentication authentication) {
        userService.deleteUser(id, authentication.getName());
        return ResponseEntity.ok("User deleted successfully");
    }


}