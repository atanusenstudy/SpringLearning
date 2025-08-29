package com.globaltechadvisor.SpringSecurityEx.service;

// ... imports ...

import com.globaltechadvisor.SpringSecurityEx.dao.UserRepo;
import com.globaltechadvisor.SpringSecurityEx.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DataInitializer {

    @Autowired
    private UserRepo userRepo;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @PostConstruct
    public void init() {
        // Create default roles and users if they don't exist
        if (userRepo.findByUsername("admin") == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(User.Role.ADMIN);
            userRepo.save(admin);
        }

        if (userRepo.findByUsername("user") == null) {
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRole(User.Role.USER);
            userRepo.save(user);
        }
    }
}