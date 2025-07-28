package com.niraj.fitforgeservice.fitforge.service;

import com.niraj.fitforgeservice.fitforge.config.JwtUtil;
import com.niraj.fitforgeservice.fitforge.dto.UpdateUserProfileRequest;
import com.niraj.fitforgeservice.fitforge.dto.UserLoginRequest;
import com.niraj.fitforgeservice.fitforge.dto.UserLoginResponse;
import com.niraj.fitforgeservice.fitforge.dto.UserProfileResponse;
import com.niraj.fitforgeservice.fitforge.entity.User;
import com.niraj.fitforgeservice.fitforge.exception.AuthenticationException;
import com.niraj.fitforgeservice.fitforge.exception.InvalidInputException;
import com.niraj.fitforgeservice.fitforge.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public User findUserById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new InvalidInputException("User not found"));
    }


    @Transactional(readOnly = true)
    public UserProfileResponse getUserProfileById(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidInputException("User not found with id: " + userId));
        return convertToDto(user);
    }

    @Transactional
    public void updateUserProfile(Integer userId, UpdateUserProfileRequest updateUserProfileRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidInputException("User not found with id: " + userId));

        if (updateUserProfileRequest.getName() != null) {
            user.setName(updateUserProfileRequest.getName());
        }
        if (updateUserProfileRequest.getAge() != null) {
            user.setAge(updateUserProfileRequest.getAge());
        }
        if (updateUserProfileRequest.getWeight() != null) {
            user.setWeightLbs(updateUserProfileRequest.getWeight());
        }
        if (updateUserProfileRequest.getHeight() != null) {
            user.setHeightCm(updateUserProfileRequest.getHeight());
        }
        if (updateUserProfileRequest.getPhotoURL() != null) {
            user.setAvatarUrl(updateUserProfileRequest.getPhotoURL());
        }

        userRepository.save(user);
    }

    private UserProfileResponse convertToDto(User user) {
        UserProfileResponse dto = new UserProfileResponse();
        dto.setUid(user.getId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setAge(user.getAge());
        dto.setWeight(user.getWeightLbs());
        dto.setHeight(user.getHeightCm());
        dto.setPhotoURL(user.getAvatarUrl());
        return dto;
    }

    public UserLoginResponse authenticate(UserLoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new AuthenticationException("Invalid credentials."));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new AuthenticationException("Invalid credentials.");
        }

        String token = jwtUtil.generateToken(user);

        UserProfileResponse userDto = new UserProfileResponse();
        userDto.setUid(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setPhotoURL(user.getAvatarUrl());
        userDto.setAge(user.getAge());
        userDto.setWeight(user.getWeightLbs());
        userDto.setHeight(user.getHeightCm());

        UserLoginResponse response = new UserLoginResponse();
        response.setToken(token);
        response.setUser(userDto);

        return response;
    }
}
