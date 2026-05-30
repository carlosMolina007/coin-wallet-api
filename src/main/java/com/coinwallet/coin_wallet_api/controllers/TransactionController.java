package com.coinwallet.coin_wallet_api.controllers;

import com.coinwallet.coin_wallet_api.models.Transaction;
import com.coinwallet.coin_wallet_api.services.TransactionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    // POST - Make a transfer (http://localhost:8080/api/transactions/transfer)
    @PostMapping("/transfer")
    public ResponseEntity<String> makeTransfer(@Valid @RequestBody TransferRequest request) {
        
        transactionService.makeTransfer(
            request.getSourceAccountId(),
            request.getTargetAccountId(),
            request.getAmount(),
            request.getDescription()
        );
        
        return ResponseEntity.ok("Transfer sent successfully");
    }

    // GET - Transactions history (http://localhost:8080/api/account/{accountId}/history)}
    @GetMapping("/account/{accountId}/history")
    public ResponseEntity<List<Transaction>> getTransactionHistory(@PathVariable Long accountId) {
        List<Transaction> transactionHistory = transactionService.getTransactionHistory(accountId);

        return ResponseEntity.ok(transactionHistory);
    }
    

    
    @Data
    public static class TransferRequest {
        @NotNull(message = "Source account ID is required")
        private Long sourceAccountId;

        @NotNull(message = "Target account ID is required")
        private Long targetAccountId;

        @Positive(message = "Amount must be greater than zero")
        @NotNull(message = "Amount is required")
        private BigDecimal amount;

        @NotNull(message = "Description is required (ex. lunch pay)")
        private String description;
    }
}