package com.globaltechadvisor.SpringSecurityEx.controller;

import com.globaltechadvisor.SpringSecurityEx.dto.RefreshTokenRequest;
import com.globaltechadvisor.SpringSecurityEx.dto.TokenResponse;
import com.globaltechadvisor.SpringSecurityEx.model.Student;
import com.globaltechadvisor.SpringSecurityEx.model.User;
import com.globaltechadvisor.SpringSecurityEx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public User register(@RequestBody User user){
        return service.register(user);
    }

    @GetMapping("/users")
    public List<User> getStudents(){
        return service.users();
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody User user) {
        return service.verify(user);
    }

    @PostMapping("/refreshtoken")
    public TokenResponse refreshtoken(@RequestBody RefreshTokenRequest request) {
        return service.refreshToken(request.getRefreshToken());
    }
}
