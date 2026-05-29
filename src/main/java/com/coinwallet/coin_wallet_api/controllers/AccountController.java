package com.coinwallet.coin_wallet_api.controllers;

import com.coinwallet.coin_wallet_api.models.Account;
import com.coinwallet.coin_wallet_api.services.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    // POST - Crear cuenta asociada a un usuario
    @PostMapping("/user/{userId}")
    public ResponseEntity<Account> createAccount(@PathVariable Long userId, @Valid @RequestBody Account account) {
        Account createdAccount = accountService.createAccount(userId, account);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }
}