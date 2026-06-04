package com.coinwallet.coin_wallet_api.controllers;

import com.coinwallet.coin_wallet_api.models.Account;
import com.coinwallet.coin_wallet_api.services.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    // POST - Create account assocciated with a user (http://localhost:8080/api/accounts/user/{userId})
    @PostMapping("/user/{userId}")
    public ResponseEntity<Account> createAccount(@PathVariable Long userId, @Valid @RequestBody Account account) {
        Account createdAccount = accountService.createAccount(userId, account);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    //GET - Get your account associated with a user logged (https://localhost:8080/api/accounts/me)
    @GetMapping("/me")
    public ResponseEntity<?> getMyAccountDetails(Principal principal) {
        
        String emailUserLogged = principal.getName();

        Account myAccount = accountService.getMyAccount(emailUserLogged);
        
        return ResponseEntity.ok(myAccount);
    }
}