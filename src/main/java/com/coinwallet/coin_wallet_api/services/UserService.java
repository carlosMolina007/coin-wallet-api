package com.coinwallet.coin_wallet_api.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coinwallet.coin_wallet_api.exceptions.ResourceNotFoundException;
import com.coinwallet.coin_wallet_api.models.User;
import com.coinwallet.coin_wallet_api.repositories.UserRepository;
import com.coinwallet.coin_wallet_api.models.Account;
import com.coinwallet.coin_wallet_api.repositories.AccountRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    //method to create a new user
    public User createUser(User newUser){
        if (userRepository.existsByEmail(newUser.getEmail())){
            throw new RuntimeException("Error: Email is already registered!");
        }

        String passwordEncrypted = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(passwordEncrypted);

        User savedUser = userRepository.save(newUser);

        Account newAccount = new Account();
        newAccount.setAccountNumber(generateAccountNumber());
        newAccount.setBalance(new BigDecimal("0"));
        newAccount.setCurrency("USD");
        newAccount.setUser(savedUser);

        accountRepository.save(newAccount);
        
        return savedUser;
    }

    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Error: user not found with ID: "+ id));
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    private String generateAccountNumber() {
    Random random = new Random();
    long randomNumber = 1000000000L + (long)(random.nextDouble() * 8999999999L);
    return "CW-" + randomNumber; // CW per CoinWallet
    }

}
