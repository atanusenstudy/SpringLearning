package com.globaltechadvisor.SpringSecurityEx.dto;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String username;
    private String password;
    // Add other fields you want users to be able to update
}