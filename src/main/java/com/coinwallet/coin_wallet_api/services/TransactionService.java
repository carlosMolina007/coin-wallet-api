package com.coinwallet.coin_wallet_api.services;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coinwallet.coin_wallet_api.models.Account;
import com.coinwallet.coin_wallet_api.models.Transaction;
import com.coinwallet.coin_wallet_api.repositories.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    @Transactional
    public Transaction makeTransfer(Long sourceAccountId, Long destinationAccountId, BigDecimal amount, String description){

        if (sourceAccountId.equals(destinationAccountId)) {
            throw new RuntimeException("Error: You can't transfer yourself"); 
        }
        
        if(amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new RuntimeException("Error: Transfer amount must be greater than zero"); 
        }

        Account sAccount = accountService.findById(sourceAccountId);
        Account dAccount = accountService.findById(destinationAccountId); 

        if (sAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Error: Insufficient funds in source account");
            }

        //Substract money to source account
        BigDecimal sAccountNewBalance = sAccount.getBalance().subtract(amount);
        sAccount.setBalance(sAccountNewBalance);

        //Add money to destination account
        BigDecimal dAccountNewBalance = dAccount.getBalance().add(amount);
        dAccount.setBalance(dAccountNewBalance);

        Transaction transaction = new Transaction();
        transaction.setSourceAccount(sAccount);
        transaction.setDestinationAccount(dAccount);
        transaction.setAmount(amount);
        transaction.setType("TRANSFER");
        transaction.setDescription(description);

        return transactionRepository.save(transaction);

    }

}
