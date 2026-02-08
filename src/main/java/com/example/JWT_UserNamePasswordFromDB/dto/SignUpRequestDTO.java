package com.example.JWT_UserNamePasswordFromDB.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class SignUpRequestDTO {

    private String name;
    private String username;
    private String dob;
    private String password;
    private String role;

}
