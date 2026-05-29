package com.coinwallet.coin_wallet_api.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.coinwallet.coin_wallet_api.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);

}
