package com.somraj.HottelMGM.dto;

import com.somraj.HottelMGM.enums.UserRole;

import lombok.Data;

@Data
public class AuthenticationResponse {
    
    private String jwt;

    private Long userId;

    private UserRole userRole;
}
