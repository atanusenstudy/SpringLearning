package com.globaltechadvisor.SpringSecurityEx.service;

import com.globaltechadvisor.SpringSecurityEx.dao.UserRepo;
import com.globaltechadvisor.SpringSecurityEx.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

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
}
