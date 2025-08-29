package com.globaltechadvisor.SpringSecurityEx.dto;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
}