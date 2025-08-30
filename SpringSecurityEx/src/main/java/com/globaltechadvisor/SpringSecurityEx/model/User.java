package com.globaltechadvisor.SpringSecurityEx.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "users")
@Entity
public class User {
    /*
    I recommend using Integer instead of int for your ID field because:
        It allows for null values, which can be useful in some scenarios
        It's consistent with JPA's expected behavior (JPA uses wrapper types)
        It allows you to use methods like .equals() for comparison
        It's more consistent with how IDs are typically handled in Spring Data JPA
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Change from int to Integer
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        USER, ADMIN, MODERATOR
    }
}