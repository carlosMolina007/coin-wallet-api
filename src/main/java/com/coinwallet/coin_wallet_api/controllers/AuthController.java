package com.coinwallet.coin_wallet_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coinwallet.coin_wallet_api.DTOs.LoginRequestDTO;
import com.coinwallet.coin_wallet_api.config.JwtService;
import com.coinwallet.coin_wallet_api.exceptions.ResourceBadRequestException;
import com.coinwallet.coin_wallet_api.models.User;
import com.coinwallet.coin_wallet_api.repositories.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    // 1. POST Validate an existing user (http://localhost:8080/api/auth/login)
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDTO loginRequest){
        User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new ResourceBadRequestException("Error: Invalid credentials"));

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new ResourceBadRequestException("Error: Invalid credentials");
        }
        
        String token = jwtService.generateToken(user.getEmail());

        return ResponseEntity.ok(token);
    }

}
