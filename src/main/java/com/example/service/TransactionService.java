package com.example.service;

import com.example.model.Transaction;
import com.example.model.User;
import com.example.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction transferMoney(User sender, User receiver, BigDecimal amount) {

        Transaction transaction = new Transaction();
        transaction.setSender(sender.getAccount());
        transaction.setReceiver(receiver.getAccount());
        transaction.setAmount(amount);

        BigDecimal senderBalance = sender.getAccount().getBalance();
        sender.getAccount().setBalance(senderBalance.subtract(amount));

        BigDecimal receiverBalance = receiver.getAccount().getBalance();
        receiver.getAccount().setBalance(receiverBalance.add(amount));

        accountService.updateAccount(sender.getAccount());
        accountService.updateAccount(receiver.getAccount());

        return transactionRepository.save(transaction);
    }
}