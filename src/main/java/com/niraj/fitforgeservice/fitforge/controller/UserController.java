package com.niraj.fitforgeservice.fitforge.controller;

import com.niraj.fitforgeservice.fitforge.dto.UserProfileResponse;
import com.niraj.fitforgeservice.fitforge.dto.UserProfileUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@Slf4j
public class UserController {

    /**
     * Get a specific user's profile
     * GET /v1/users/{user_id}
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileResponse> getUserProfile(
            @RequestHeader("Authorization") String authToken,
            @PathVariable("userId") String userId) {

        log.info("GET /v1/users/{} (Auth: {})", userId, authToken);
        UserProfileResponse response = new UserProfileResponse(
                userId, "Niraj Kumar", 168, 27, "nirajkum165@gmail.com", "Fitness enthusiast and Software Engineer");
        return ResponseEntity.ok(response);
    }

    /**
     * Update a user's profile
     * PATCH /v1/users/{user_id}
     */
    @PatchMapping("/{userId}")
    public ResponseEntity<UserProfileResponse> updateUserProfile(
            @RequestHeader("Authorization") String authToken,
            @PathVariable("userId") String userId,
            @RequestBody UserProfileUpdateRequest updateRequest) {

        log.info("PATCH /v1/users/{} (Auth: {})", userId, authToken);
        log.info("Update Request: {}", updateRequest);

        UserProfileResponse response = new UserProfileResponse(
                userId,
                updateRequest.name() != null ? updateRequest.name() : "Niraj Kumar",
                updateRequest.weight_lbs() != null ? updateRequest.weight_lbs() : 168,
                updateRequest.age() != null ? updateRequest.age() : 27,
                "nirajkum165@gmail.com",
                "Fitness enthusiast and Software Engineer");
        return ResponseEntity.ok(response);
    }

    /**
     * Delete a user (Soft Delete)
     * DELETE /v1/users/{user_id}
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(
            @RequestHeader("Authorization") String authToken,
            @PathVariable("userId") String userId) {
        log.info("DELETE /v1/users/{} (Auth: {})", userId, authToken);
        return ResponseEntity.noContent().build();
    }
}