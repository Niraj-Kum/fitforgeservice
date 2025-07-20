package com.niraj.fitforgeservice.fitforge.service;

import com.niraj.fitforgeservice.fitforge.entity.User;
import com.niraj.fitforgeservice.fitforge.exception.InvalidInputException;
import com.niraj.fitforgeservice.fitforge.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new InvalidInputException("User not found"));
    }
}
