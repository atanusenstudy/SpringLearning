package com.globaltechadvisor.SpringSecurityEx.dao;

import com.globaltechadvisor.SpringSecurityEx.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.globaltechadvisor.SpringSecurityEx.model.User.Role;


// This will tell it is repository, and we are using JPA for connecting the Db
// Also we are specifying Table name and Primary key(Id)
@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    User findByUsername(String username);
    long countByRole(Role role);
}
