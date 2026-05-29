package com.coinwallet.coin_wallet_api.services;

import org.springframework.stereotype.Service;

import com.coinwallet.coin_wallet_api.models.Account;
import com.coinwallet.coin_wallet_api.models.User;
import com.coinwallet.coin_wallet_api.repositories.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserService userService;

    public Account createAccount(Long userId, Account newAccount){

        User userExists = userService.getUserById(userId);
            if(accountRepository.existsByAccountNumber(newAccount.getAccountNumber())){
            throw new RuntimeException("Error: Account number already exists!");          
        }
        
        newAccount.setUser(userExists);
        return accountRepository.save(newAccount);
        
    }

    public Account findById(Long id){
        return accountRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Error: account not found with ID: "+ id));
    }

    public Account getAccountByNumberAccount(String numberAccount){
        return accountRepository.findByAccountNumber(numberAccount)
                    .orElseThrow(() -> new RuntimeException("Error: account not found with number: "+numberAccount));
    }



}
