package com.coinwallet.coin_wallet_api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coinwallet.coin_wallet_api.models.User;
import com.coinwallet.coin_wallet_api.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;

    // 1. POST - Create new user (http://localhost:8080/api/users)
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User createdUser = userService.createUser(user);

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // 2. GET - get user by ID (http://localhost:8080/api/users/{id})
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user); // Return state: 200 (OK)
    }

    // 3. GET - Traer todos los usuarios (http://localhost:8080/api/users)
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    

}
