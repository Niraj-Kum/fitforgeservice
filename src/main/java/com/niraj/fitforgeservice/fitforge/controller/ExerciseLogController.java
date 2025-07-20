package com.niraj.fitforgeservice.fitforge.controller;

import com.niraj.fitforgeservice.fitforge.dto.ExerciseLogCreateRequest;
import com.niraj.fitforgeservice.fitforge.dto.ExerciseLogResponse;
import com.niraj.fitforgeservice.fitforge.dto.ExerciseLogUpdateRequest;
import com.niraj.fitforgeservice.fitforge.dto.FitForgeResponse;
import com.niraj.fitforgeservice.fitforge.service.ExerciseLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/v1")
@Slf4j
public class ExerciseLogController {

    private final ExerciseLogService exerciseLogService;

    public ExerciseLogController(ExerciseLogService exerciseLogService) {
        this.exerciseLogService = exerciseLogService;
    }


    /**
     * Get all exercise logs for a user
     * GET /v1/users/{user_id}/exercise-logs
     */
    @GetMapping("/users/{userId}/exercise-logs")
    public ResponseEntity<Map<String , List<ExerciseLogResponse>>> getUserExerciseLogs(
            @RequestHeader("Authorization") String authToken,
            @PathVariable("userId") Integer userId) {

        log.info("GET /v1/users/{}/exercise-logs (Auth: {})", userId, authToken);
        return ResponseEntity.ok(exerciseLogService.getExerciseLogsByUserId(userId));
    }

    /**
     * Create a new exercise log entry
     * POST /v1/exercise-logs
     */
    @PostMapping("/exercise-logs")
    public ResponseEntity<FitForgeResponse<String>> createExerciseLog(
            @RequestHeader("Authorization") String authToken,
            @RequestBody ExerciseLogCreateRequest createRequest) {

        log.info("POST /v1/exercise-logs (Auth: {})", authToken);
        log.info("Create Request: {}", createRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new FitForgeResponse<>(exerciseLogService.createLog(createRequest)));
    }

    /**
     * Update an exercise log entry
     * PATCH /v1/exercise-logs/{log_id}
     */
    @PatchMapping("/exercise-logs/{logId}")
    public ResponseEntity<FitForgeResponse<String>> updateExerciseLog(
            @RequestHeader("Authorization") String authToken,
            @PathVariable("logId") Integer logId,
            @RequestBody ExerciseLogUpdateRequest updateRequest) {

        log.info("PATCH /v1/exercise-logs/{} (Auth: {})", logId, authToken);
        log.info("Update Request: {}", updateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new FitForgeResponse<>(exerciseLogService.updateLog(updateRequest, logId)));
    }

    /**
     * Delete an exercise log entry
     * DELETE /v1/exercise-logs/{log_id}
     */
    @DeleteMapping("/exercise-logs/{logId}")
    public ResponseEntity<Void> deleteExerciseLog(
            @RequestHeader("Authorization") String authToken,
            @PathVariable("logId") Integer logId) {
        log.info("DELETE /v1/exercise-logs/{} (Auth: {})", logId, authToken);
        exerciseLogService.deleteLog(logId);
        return ResponseEntity.noContent().build();
    }
}