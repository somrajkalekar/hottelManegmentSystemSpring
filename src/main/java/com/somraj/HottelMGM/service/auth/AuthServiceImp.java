package com.somraj.HottelMGM.service.auth;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.somraj.HottelMGM.dto.SignupRequest;
import com.somraj.HottelMGM.dto.UserDto;
import com.somraj.HottelMGM.entity.User;
import com.somraj.HottelMGM.enums.UserRole;
import com.somraj.HottelMGM.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService{
    
    private final UserRepository userRepository;

    @PostConstruct
    public void createAdminAccount(){
        
        Optional<User> adminAccount = userRepository.findByUserRole(UserRole.ADMIN);

        if (adminAccount.isEmpty()) {
            User user = new User();
        user.setEmail("admin@test.com");
        user.setName("Admin");
        user.setUserRole(UserRole.ADMIN);
        user.setPassword(new BCryptPasswordEncoder().encode("admin"));

        userRepository.save(user);

        System.out.println("Admin Account Created Successfully... ");    
        }
        else{
            System.out.println("Admin Account Already Exist...");
        }
    }

    public UserDto createUser(SignupRequest signupRequest){

        if (userRepository.findFirstByEmail(signupRequest.getEmail()).isPresent()) {
            throw new EntityExistsException("User Already Present With Email");
        }

        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setUserRole(UserRole.CUSTOMER);

        User createdUser = userRepository.save(user);

        return createdUser.getUserDto();
    }

}
