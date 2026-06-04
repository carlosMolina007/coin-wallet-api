package com.coinwallet.coin_wallet_api.services;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coinwallet.coin_wallet_api.exceptions.ResourceBadRequestException;
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
    public Transaction makeTransfer(Long sourceAccountId, String destinationAccountNumber, BigDecimal amount, String description){

        Account destinationAccount = accountService.getAccountByNumberAccount(destinationAccountNumber);

        Long destinationAccountId = destinationAccount.getId();

        if (sourceAccountId.equals(destinationAccountId)) {
            throw new ResourceBadRequestException("Error: You can't transfer yourself"); 
        }
        
        if(amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new ResourceBadRequestException("Error: Transfer amount must be greater than zero"); 
        }

        Account sAccount = accountService.findById(sourceAccountId);
        Account dAccount = accountService.findById(destinationAccountId); 

        if (sAccount.getBalance().compareTo(amount) < 0) {
            throw new ResourceBadRequestException("Error: Insufficient funds in source account, available: $" + sAccount.getBalance());
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

    public Page<Transaction> getTransactionHistory(Long accountId, int page, int size){
        accountService.findById(accountId);
        PageRequest pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return transactionRepository.findBySourceAccountIdOrDestinationAccountId(accountId, accountId, pageable);
    }

}
