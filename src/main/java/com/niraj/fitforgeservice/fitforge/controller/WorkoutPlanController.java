package com.niraj.fitforgeservice.fitforge.controller;

import com.niraj.fitforgeservice.fitforge.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1")
@Slf4j
public class WorkoutPlanController {

    /**
     * Get all workout plans for a user
     * GET /v1/users/{user_id}/workout-plans
     */
    @GetMapping("/users/{userId}/workout-plans")
    public ResponseEntity<List<WorkoutPlanResponse>> getUserWorkoutPlans(
            @RequestHeader("Authorization") String authToken,
            @PathVariable("userId") String userId) {

        log.info("GET /v1/users/{}/workout-plans (Auth: {})", userId, authToken);
        List<WorkoutPlanResponse> plans = Arrays.asList(
                new WorkoutPlanResponse(
                        UUID.randomUUID().toString(),
                        userId,
                        "Beginner Full Body",
                        LocalDate.of(2025, 1, 1),
                        true,
                        Collections.emptyList()
                ),
                new WorkoutPlanResponse(
                        UUID.randomUUID().toString(),
                        userId,
                        "Advanced Split",
                        LocalDate.of(2024, 10, 1),
                        false,
                        Collections.emptyList()
                )
        );
        return ResponseEntity.ok(plans);
    }

    /**
     * Get a single, detailed workout plan
     * GET /v1/workout-plans/{plan_id}
     */
    @GetMapping("/workout-plans/{planId}")
    public ResponseEntity<WorkoutPlanResponse> getDetailedWorkoutPlan(
            @RequestHeader("Authorization") String authToken,
            @PathVariable("planId") String planId) {

        log.info("GET /v1/workout-plans/{} (Auth: {})", planId, authToken);
        WorkoutPlanResponse plan = new WorkoutPlanResponse(
                planId,
                "user-uuid-123",
                "My New Custom Plan",
                LocalDate.of(2025, 1, 1),
                true,
                Arrays.asList(
                        new WorkoutDay(
                                1,
                                "Push Day",
                                "Strength",
                                "10 min cardio",
                                Arrays.asList(
                                        new ExerciseCategory("Chest", List.of(new Exercise("Bench Press", "5x5"))),
                                        new ExerciseCategory("Shoulders", List.of(new Exercise("OHP", "3x8")))
                                )
                        ),
                        new WorkoutDay(
                                2,
                                "Pull Day",
                                "Hypertrophy",
                                "Dynamic stretches",
                                Arrays.asList(
                                        new ExerciseCategory("Back", List.of(new Exercise("Deadlifts", "3x5"))),
                                        new ExerciseCategory("Biceps", List.of(new Exercise("Bicep Curls", "3x12")))
                                )
                        )
                )
        );
        return ResponseEntity.ok(plan);
    }

    /**
     * Create a new workout plan
     * POST /v1/workout-plans
     */
    @PostMapping("/workout-plans")
    public ResponseEntity<WorkoutPlanResponse> createWorkoutPlan(
            @RequestHeader("Authorization") String authToken,
            @RequestBody WorkoutPlanCreateRequest createRequest) {

        log.info("POST /v1/workout-plans (Auth: {})", authToken);
        log.info("Create Request: {}", createRequest);
        WorkoutPlanResponse newPlan = new WorkoutPlanResponse(
                UUID.randomUUID().toString(),
                createRequest.user_id(),
                createRequest.name(),
                createRequest.start_date(),
                createRequest.is_active(),
                createRequest.days()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(newPlan);
    }

    /**
     * Update a workout plan
     * PATCH /v1/workout-plans/{plan_id}
     */
    @PatchMapping("/workout-plans/{planId}")
    public ResponseEntity<WorkoutPlanResponse> updateWorkoutPlan(
            @RequestHeader("Authorization") String authToken,
            @PathVariable("planId") String planId,
            @RequestBody WorkoutPlanUpdateRequest updateRequest) {

        log.info("PATCH /v1/workout-plans/{} (Auth: {})", planId, authToken);
        log.info("Update Request: {}", updateRequest);

        WorkoutPlanResponse updatedPlan = new WorkoutPlanResponse(
                planId,
                "user-uuid-123",
                updateRequest.name() != null ? updateRequest.name() : "Original Plan Name",
                LocalDate.of(2025, 1, 1),
                updateRequest.is_active() != null ? updateRequest.is_active() : true,
                updateRequest.days() != null ? updateRequest.days() : Collections.emptyList()
        );
        return ResponseEntity.ok(updatedPlan);
    }

    /**
     * Delete a workout plan
     * DELETE /v1/workout-plans/{plan_id}
     */
    @DeleteMapping("/workout-plans/{planId}")
    public ResponseEntity<Void> deleteWorkoutPlan(
            @RequestHeader("Authorization") String authToken,
            @PathVariable("planId") String planId) {
        log.info("DELETE /v1/workout-plans/{} (Auth: {})", planId, authToken);
        return ResponseEntity.noContent().build();
    }
}