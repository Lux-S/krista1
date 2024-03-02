package com.example.service;

import com.example.model.Account;
import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(String username, String password, String email, String phone, String fullName, Date dateOfBirth, BigDecimal initialBalance) {

        if (userRepository.findByUsername(username) != null) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByEmail(email) != null) {
            throw new RuntimeException("Email already exists");
        }
        if (userRepository.findByPhone(phone) != null) {
            throw new RuntimeException("Phone already exists");
        }

        if (dateOfBirth != null && !isAdult(dateOfBirth)) {
            throw new RuntimeException("User must be at least 18 years old");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setFullName(fullName);
        user.setDateOfBirth(dateOfBirth);

        Account account = new Account();
        account.setUser(user);
        account.setBalance(initialBalance);
        user.setAccount(account);

        return userRepository.save(user);
    }

    private boolean isAdult(Date dateOfBirth) {

        return false;
    }

}