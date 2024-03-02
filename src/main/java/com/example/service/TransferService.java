package com.example.service;

import com.example.model.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransferService {

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public void transferMoney(Account sender, Account receiver, BigDecimal amount) {

        if (sender == null || sender.getBalance() == null || sender.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Отправитель не имеет достаточного баланса для перевода");
        }

        if (receiver == null) {
            throw new IllegalArgumentException("Получатель не найден");
        }

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        accountRepository.save(sender);
        accountRepository.save(receiver);
    }
}