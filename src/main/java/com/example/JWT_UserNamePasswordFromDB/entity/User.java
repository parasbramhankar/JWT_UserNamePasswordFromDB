package com.example.JWT_UserNamePasswordFromDB.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true,length = 50)
    private String username;

    private LocalDate dob;

    @Column(nullable = false)
    private String password;

    private String role;

}
