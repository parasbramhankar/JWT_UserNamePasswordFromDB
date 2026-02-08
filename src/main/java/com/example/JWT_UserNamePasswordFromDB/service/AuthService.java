package com.example.JWT_UserNamePasswordFromDB.service;

import com.example.JWT_UserNamePasswordFromDB.dto.JwtResponseDTO;
import com.example.JWT_UserNamePasswordFromDB.dto.SignInRequestDTO;
import com.example.JWT_UserNamePasswordFromDB.dto.SignUpRequestDTO;
import com.example.JWT_UserNamePasswordFromDB.entity.User;
import com.example.JWT_UserNamePasswordFromDB.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private AuthenticationManager manager;

    public String register(SignUpRequestDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setDob(dto.getDob());
        user.setRole(dto.getRole());
        user.setPassword(dto.getPassword());

        userRepository.save(user);

        return "user registered";
    }






    public JwtResponseDTO login(SignInRequestDTO dto) {

       Authentication authentication= manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword()
                )
        );

       String username= authentication.getName();

        String token = jwtService.generateToken(username);

        return new JwtResponseDTO(token);
    }
}
/**
 LOGIN FLOW EXPLANATION

 Step 1:
 Client username aur password bhejta hai.
 Ye values SignInRequestDTO me milti hain.

 Step 2:
 AuthenticationManager.authenticate() ko call kiya jata hai.
 Hum UsernamePasswordAuthenticationToken bana ke dete hain.

 Iska matlab:
 "Ye credentials hain. Check karo sahi hain ya nahi."

 Step 3 (Internal framework work):
 authenticate() ke andar:
    -> UserDetailsService call hota hai
    -> Database se user load hota hai
    -> PasswordEncoder password match karta hai

 Step 4:
 Agar password galat hai -> Exception throw hota hai.
 Method yahi ruk jata hai.
 JWT generate nahi hota.

 Step 5:
 Agar exception nahi aaya -> authentication successful.

 Step 6:
 Ab system JWT token generate karta hai.
 Token me usually username identity hoti hai.

 Step 7:
 Ye token client ko return kar diya jata hai.

 Step 8:
 Client future requests me Authorization header me:
    Bearer <token>
 bhejega.

 Fir har request me JWT filter:
    -> token validate karega
    -> username nikalega
    -> user ko SecurityContext me set karega.

 IMPORTANT:
 Yaha hum DTO se username leke token bana rahe hain.
 More safe approach hota hai authenticate() ke result se username lena,
 kyunki wahi trusted hota hai.

 Example better version:
 Authentication auth = manager.authenticate(...);
 String username = auth.getName();

 FINAL:
 Credentials verified -> token issued -> token se APIs access.
*/


