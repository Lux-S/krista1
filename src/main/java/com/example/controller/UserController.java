package com.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.model.User;
import com.example.service.UserService;

import java.math.BigDecimal;
import java.util.Date;

@RestController
@RequestMapping("/api/users")
@Api(value = "UserController", description = "Операции с пользователями")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @ApiOperation(value = "Создание пользователя")
    public User createUser(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String email,
                           @RequestParam String phone,
                           @RequestParam String fullName,
                           @RequestParam Date dateOfBirth,
                           @RequestParam BigDecimal initialBalance) {
        return userService.createUser(username, password, email, phone, fullName, dateOfBirth, initialBalance);
    }
}