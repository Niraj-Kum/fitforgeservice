package com.niraj.fitforgeservice.fitforge.service;

import com.niraj.fitforgeservice.fitforge.dto.UpdateUserProfileRequest;
import com.niraj.fitforgeservice.fitforge.dto.UserLoginRequest;
import com.niraj.fitforgeservice.fitforge.dto.UserProfileResponse;
import com.niraj.fitforgeservice.fitforge.entity.User;
import com.niraj.fitforgeservice.fitforge.exception.InvalidInputException;
import com.niraj.fitforgeservice.fitforge.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    public void loginUser(UserLoginRequest userLoginRequest) {
        User user = userRepository.findByEmail(userLoginRequest.getEmail()).orElseThrow(() -> new InvalidInputException("Email does not exist"));


    }
}
