package com.globaltechadvisor.SpringSecurityEx.dto;

import com.globaltechadvisor.SpringSecurityEx.model.User;
import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String username;
    private User.Role role;

    public static UserDTO fromUser(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        return dto;
    }
}