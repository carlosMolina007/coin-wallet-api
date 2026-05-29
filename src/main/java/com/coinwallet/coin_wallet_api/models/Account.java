package com.coinwallet.coin_wallet_api.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "accounts")

public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank(message = "Account number is required")
    @Size(min = 4, max = 16, message = "account number must be between 4 and 16 characters")
    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @NotNull(message = "Balance is required")
    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @NotBlank(message = "Currency is required (COP, USD, EUR) ")
    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
