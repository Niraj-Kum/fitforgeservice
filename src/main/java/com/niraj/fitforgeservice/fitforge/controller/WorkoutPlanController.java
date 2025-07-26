package com.niraj.fitforgeservice.fitforge.controller;

import com.niraj.fitforgeservice.fitforge.dto.*;
import com.niraj.fitforgeservice.fitforge.entity.WorkoutPlan;
import com.niraj.fitforgeservice.fitforge.service.WorkoutPlanService;
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
@RequestMapping("/v1/workoutPlan")
@Slf4j
public class WorkoutPlanController {

    private final WorkoutPlanService workoutPlanService;

    public WorkoutPlanController(WorkoutPlanService workoutPlanService) {
        this.workoutPlanService = workoutPlanService;
    }

    /**
     * Get all workout plans for a user
     * GET /v1/users/{user_id}
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserWorkoutPlan> getUserWorkoutPlan(
            @PathVariable("userId") Integer userId) {

        log.info("GET /v1/users/{}", userId);
        UserWorkoutPlan plan = workoutPlanService.getUserWorkoutPlan(userId);
        return ResponseEntity.ok(plan);
    }

    @PatchMapping("/{planId}")
    public ResponseEntity<Void> updateWorkoutPlan(
            @PathVariable Integer planId,
            @RequestBody UpdateWorkoutPlanDto updateDto) {
        workoutPlanService.updatePlan(planId, updateDto);
        return ResponseEntity.ok().build();
    }

    /**
     * Create a new workout plan
     * POST /v1
     */
    @PostMapping("/{userId}")
    public ResponseEntity<WorkoutPlanResponse> createWorkoutPlan(
            @PathVariable("userId") Integer userId,
            @RequestBody CreateWorkoutPlanRequest createRequest) {

        log.info("POST /v1s");
        log.info("Create Request: {}", createRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(workoutPlanService.createWorkoutPlan(userId, createRequest));
    }

    @PostMapping("/{userId}/pause")
    public ResponseEntity<String> pauseWorkoutPlan(
            @PathVariable("userId") Integer userId) {

        log.info("POST /v1");
        return ResponseEntity.status(HttpStatus.CREATED).body(workoutPlanService.pauseWorkout(userId));
    }

    @PostMapping("/{userId}/resume")
    public ResponseEntity<String> resumeWorkoutPlan(
            @PathVariable("userId") Integer userId) {

        log.info("POST /v1");
        return ResponseEntity.status(HttpStatus.CREATED).body(workoutPlanService.resumeWorkout(userId));
    }

    /**
     * Delete a workout plan
     * DELETE /v1s/{plan_id}
     */
    @DeleteMapping("s/{planId}")
    public ResponseEntity<Void> deleteWorkoutPlan(
            @PathVariable("userId") Integer userId,
            @PathVariable("planId") Integer planId) {
        log.info("DELETE /v1s/{} (Auth: {})", planId, userId);
        String message = workoutPlanService.archiveWorkoutPlan(userId, planId);
        return ResponseEntity.noContent().build();
    }
}