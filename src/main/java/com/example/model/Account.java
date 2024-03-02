package com.example.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private BigDecimal balance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void updateBalance() {
        BigDecimal currentBalance = this.balance;
        BigDecimal increase = currentBalance.multiply(new BigDecimal("0.05")); // увеличение на 5%

        BigDecimal maxAllowedBalance = currentBalance.multiply(new BigDecimal("2.07"));

        if (increase.compareTo(maxAllowedBalance.subtract(currentBalance)) > 0) {
            this.balance = maxAllowedBalance;
        } else {
            this.balance = currentBalance.add(increase).setScale(2, RoundingMode.HALF_UP);
        }
    }
}