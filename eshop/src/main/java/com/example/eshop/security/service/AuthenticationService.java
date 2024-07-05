package com.example.eshop.security.service;


import com.example.eshop.model.Role;
import com.example.eshop.repository.UserRepository;
import com.example.eshop.security.AuthenticationResponse;
import com.example.eshop.security.dtoAuth.AuthenticationRequest;
import com.example.eshop.security.dtoAuth.RegisterRequest;
import com.example.eshop.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import com.example.eshop.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  JwtService jwtService;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private TokenBlacklistService tokenBlackListService;


    public String register(RegisterRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(request.getUsername(),
                encodedPassword,
                request.getFullName(),
                request.getEmail(),
                Role.USER);
        userRepository.save(user);
        return ("Registered Successfully");
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public String logout(String authHeader) {
        String token = jwtService.extractTokenFromHeader(authHeader);
        Date expirationDate = jwtService.extractExpiration(token);
        tokenBlackListService.blacklistToken(token, expirationDate);
        return "Successfully logged out (Token blacklisted)";
    }
}
