package com.coinwallet.coin_wallet_api.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coinwallet.coin_wallet_api.exceptions.ResourceBadRequestException;
import com.coinwallet.coin_wallet_api.models.Account;
import com.coinwallet.coin_wallet_api.models.Transaction;
import com.coinwallet.coin_wallet_api.repositories.TransactionRepository;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void ShouldTransferMoneySuccessfully(){
        //Arrange
        Account sourceAccount = new Account();
        sourceAccount.setId(1L);
        sourceAccount.setBalance(new BigDecimal("2000.0"));

        Account destinationAccount = new Account();
        destinationAccount.setId(2L);
        destinationAccount.setBalance(new BigDecimal("500.0"));

        when(accountService.findById(1L)).thenReturn(sourceAccount);
        when(accountService.findById(2L)).thenReturn(destinationAccount);


        //Act
        //transactionService.makeTransfer(1L, 2L, new BigDecimal("500.0"), "A gift for you");
    
        //Assert
        assertEquals(new BigDecimal("1500.0"), sourceAccount.getBalance());
        assertEquals(new BigDecimal("1000.0"), destinationAccount.getBalance());
    
        verify(transactionRepository, times(1)).save(any(Transaction.class)); 

    }


    @Test
    void shouldThrowExceptionWhenInsufficientFunds(){
        //Arrange
        Account sourceAccount = new Account();
        sourceAccount.setId(1L);
        sourceAccount.setBalance(new BigDecimal("100.0"));

        Account destinationAccount = new Account();
        destinationAccount.setId(2L);
        destinationAccount.setBalance(new BigDecimal("500.0"));

        when(accountService.findById(1L)).thenReturn(sourceAccount);
        when(accountService.findById(2L)).thenReturn(destinationAccount);

        //Act and Assert
        Exception exception = assertThrows(ResourceBadRequestException.class, () -> {
            //transactionService.makeTransfer(1L, 2L, new BigDecimal("500.0"), "A gift for you");
        });

        assertNotNull(exception);
        verify(transactionRepository, never()).save(any());

    }

}
