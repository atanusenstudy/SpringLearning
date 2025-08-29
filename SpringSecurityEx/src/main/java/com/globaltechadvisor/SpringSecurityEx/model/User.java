package com.globaltechadvisor.SpringSecurityEx.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;

    // Add role field
    @Enumerated(EnumType.STRING)
    private Role role;

    // Enum for roles
    public enum Role {
        USER, ADMIN, MODERATOR
    }
}