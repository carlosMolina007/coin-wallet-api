package com.coinwallet.coin_wallet_api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.coinwallet.coin_wallet_api.models.User;
import com.coinwallet.coin_wallet_api.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //method to create a new user
    public User createUser(User newUser){
        if (userRepository.existsByEmail(newUser.getEmail())){
            throw new RuntimeException("Error: Email is already registered!");
        }

        //next-time i will hash newUser password

        return userRepository.save(newUser);
    }

    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: user not found with ID: "+ id));
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

}
