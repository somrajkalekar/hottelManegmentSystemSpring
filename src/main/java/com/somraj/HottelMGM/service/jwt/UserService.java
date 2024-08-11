package com.somraj.HottelMGM.service.jwt;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    
    UserDetailsService userDetailsService();
}
