package com.somraj.HottelMGM.dto;

import com.somraj.HottelMGM.enums.UserRole;

import lombok.Data;

@Data
public class UserDto {

    private Long id;

    private String email;

    private String name;

    private UserRole UserRole;

}

