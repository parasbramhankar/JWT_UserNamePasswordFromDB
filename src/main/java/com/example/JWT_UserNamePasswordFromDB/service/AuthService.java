package com.example.JWT_UserNamePasswordFromDB.service;

import com.example.JWT_UserNamePasswordFromDB.dto.JwtResponseDTO;
import com.example.JWT_UserNamePasswordFromDB.dto.SignInRequestDTO;
import com.example.JWT_UserNamePasswordFromDB.dto.SignUpRequestDTO;
import com.example.JWT_UserNamePasswordFromDB.entity.User;
import com.example.JWT_UserNamePasswordFromDB.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private AuthenticationManager manager;

    public String register(SignUpRequestDTO dto){
        User user=new User();
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setDob(dto.getDob());
        user.setRole(dto.getRole());
        user.setPassword(dto.getPassword());

        userRepository.save(user);

        return "user registered";
    }

    public JwtResponseDTO login(SignInRequestDTO dto){
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword()
                )
        );

        String token= jwtService.generateToken(dto.getUsername());

        return new JwtResponseDTO(token);
    }
}
