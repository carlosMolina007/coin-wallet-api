package com.coinwallet.coin_wallet_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.coinwallet.coin_wallet_api.models.Account;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

    Optional<Account> findByAccountNumber(String accountNumber);
    Optional<Account> findByUser_Email(String email);
    Boolean existsByAccountNumber(String accountNumber);

}
