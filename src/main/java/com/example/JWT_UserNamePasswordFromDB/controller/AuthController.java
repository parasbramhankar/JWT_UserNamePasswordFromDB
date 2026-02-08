package com.example.JWT_UserNamePasswordFromDB.controller;

import com.example.JWT_UserNamePasswordFromDB.dto.JwtResponseDTO;
import com.example.JWT_UserNamePasswordFromDB.dto.SignInRequestDTO;
import com.example.JWT_UserNamePasswordFromDB.dto.SignUpRequestDTO;
import com.example.JWT_UserNamePasswordFromDB.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService service;

    @PostMapping("/signup")
    public String register(@RequestBody SignUpRequestDTO dto) {
        return service.register(dto);
    }

    @PostMapping("/login")
    public JwtResponseDTO login(@RequestBody SignInRequestDTO dto) {
        return service.login(dto);
    }
}
