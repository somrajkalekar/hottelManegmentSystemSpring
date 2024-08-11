package com.somraj.HottelMGM.service.auth;

import com.somraj.HottelMGM.dto.SignupRequest;
import com.somraj.HottelMGM.dto.UserDto;

public interface AuthService {
    
    UserDto createUser(SignupRequest signupRequest);
}
