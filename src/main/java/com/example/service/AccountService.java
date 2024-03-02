package com.example.service;

import com.example.model.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account updateAccount(Account account) {

        account.updateBalance();
        return accountRepository.save(account);
    }
}