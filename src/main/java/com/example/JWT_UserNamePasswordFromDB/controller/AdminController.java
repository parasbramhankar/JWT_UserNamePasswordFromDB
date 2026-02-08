package com.example.JWT_UserNamePasswordFromDB.controller;

import com.example.JWT_UserNamePasswordFromDB.entity.User;
import com.example.JWT_UserNamePasswordFromDB.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    AdminService service;

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.ok(service.getAllUser());
    }

}
