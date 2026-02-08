package com.example.JWT_UserNamePasswordFromDB.service;

import com.example.JWT_UserNamePasswordFromDB.entity.User;
import com.example.JWT_UserNamePasswordFromDB.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {


    private final UserRepository userRepository;

    public List<User> getAllUser() {

        return userRepository.findAll();
    }
}
