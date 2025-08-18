package com.globaltechadvisor.SpringSecurityEx.dao;

import com.globaltechadvisor.SpringSecurityEx.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}
