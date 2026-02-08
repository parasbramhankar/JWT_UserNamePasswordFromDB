package com.example.JWT_UserNamePasswordFromDB.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/balance")
    public String getBalance() {
        return "your balance is 100000";
    }
}
