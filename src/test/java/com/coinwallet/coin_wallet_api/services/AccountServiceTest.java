package com.coinwallet.coin_wallet_api.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coinwallet.coin_wallet_api.exceptions.ResourceNotFoundException;
import com.coinwallet.coin_wallet_api.models.Account;
import com.coinwallet.coin_wallet_api.repositories.AccountRepository;


@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void shouldReturnAccountWhenIdExists() {
        
        //Arrange
        Account mockAccount = new Account();
        mockAccount.setId(1L);
        mockAccount.setBalance(new BigDecimal(5000.0));

        when(accountRepository.findById(1L)).thenReturn(Optional.of(mockAccount));

        //Act
        Account result = accountService.findById(1L);

        //Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(new BigDecimal(5000.0), result.getBalance());
        
        verify(accountRepository, times(1)).findById(1L); 
    }

    @Test
    void shouldThrowExceptionWhenAccountDoesNotExist() {
        
        //ARRANGE
        when(accountRepository.findById(99L)).thenReturn(Optional.empty());

        // ACT & ASSERT
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            accountService.findById(99L);
        });

        assertNotNull(exception);
        verify(accountRepository, times(1)).findById(99L);
    }
}