package com.niraj.fitforgeservice.fitforge.controller;

import com.niraj.fitforgeservice.fitforge.dto.*;
import com.niraj.fitforgeservice.fitforge.service.AchievementService;
import com.niraj.fitforgeservice.fitforge.service.StrengthProgressService;
import com.niraj.fitforgeservice.fitforge.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/users")
@Slf4j
public class UserController {

    private final StrengthProgressService strengthProgressService;
    private final AchievementService achievementService;
    private final UserService userService;

    public UserController(StrengthProgressService strengthProgressService, AchievementService achievementService, UserService userService) {
        this.strengthProgressService = strengthProgressService;
        this.achievementService = achievementService;
        this.userService = userService;
    }

    @GetMapping("/{userId}/strength-progress")
    public ResponseEntity<Map<String, List<StrengthProgressDto>>> getStrengthProgress(
            @PathVariable Integer userId) {

        Map<String, List<StrengthProgressDto>> progressData = strengthProgressService.calculateStrengthProgress(userId);
        return ResponseEntity.ok(progressData);
    }

    @GetMapping("/{userId}/achievements")
    public ResponseEntity<List<AchievementDto>> getAchievements(@PathVariable Integer userId) {
        List<AchievementDto> achievements = achievementService.getUserAchievements(userId);
        return ResponseEntity.ok(achievements);
    }

    /**
     * Retrieves a specific user's profile.
     * Corresponds to: GET /v1/users/{userId}
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileResponse> getUserProfile(@PathVariable Integer userId) {
        UserProfileResponse userProfile = userService.getUserProfileById(userId);
        return ResponseEntity.ok(userProfile);
    }

    /**
     * Updates a user's profile with the provided data.
     * Corresponds to: PATCH /v1/users/{userId}
     */
    @PatchMapping("/{userId}")
    public ResponseEntity<Void> updateUserProfile(
            @PathVariable Integer userId,
            @RequestBody UpdateUserProfileRequest updateUserProfileRequest) {
        userService.updateUserProfile(userId, updateUserProfileRequest);
        return ResponseEntity.noContent().build();
    }
}