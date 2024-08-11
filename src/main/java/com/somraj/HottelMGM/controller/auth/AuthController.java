package com.somraj.HottelMGM.controller.auth;

import java.util.Optional;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.somraj.HottelMGM.dto.AuthenticationRequest;
import com.somraj.HottelMGM.dto.AuthenticationResponse;
import com.somraj.HottelMGM.dto.SignupRequest;

import com.somraj.HottelMGM.dto.UserDto;
import com.somraj.HottelMGM.entity.User;
import com.somraj.HottelMGM.repository.UserRepository;
import com.somraj.HottelMGM.service.auth.AuthService;
import com.somraj.HottelMGM.service.jwt.UserService;
import com.somraj.HottelMGM.util.JwtUtil;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
 
    //@Autowired
    private final AuthService authService;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final UserService userService;
    
    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
        try{
            UserDto createdUser = authService.createUser(signupRequest);
            return new ResponseEntity<>(createdUser, HttpStatus.OK);
        }catch(EntityExistsException entityExistsException){
            return new ResponseEntity<>("User Already Exist", HttpStatus.NOT_ACCEPTABLE);
        }catch(Exception e){
            return new ResponseEntity<>("User Not Created, Come Again Latter", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public AuthenticationResponse authenticationResponse(@RequestBody AuthenticationRequest authenticationRequest){
       
       try {
        authenticationManager.authenticate(new
        UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));

       } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect Username or Password");
       }

       final UserDetails  userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
       Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
       final String jwt = jwtUtil.generateToken(userDetails); 

       AuthenticationResponse authenticationResponse = new AuthenticationResponse();
       if (optionalUser.isPresent()) {
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
            authenticationResponse.setUserId(optionalUser.get().getId());
       }
       return authenticationResponse;
    }
}
