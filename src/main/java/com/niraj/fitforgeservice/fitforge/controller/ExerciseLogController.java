package com.niraj.fitforgeservice.fitforge.controller;

import com.niraj.fitforgeservice.fitforge.dto.*;
import com.niraj.fitforgeservice.fitforge.service.ExerciseLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/v1/exercise-logs")
@Slf4j
public class ExerciseLogController {

    private final ExerciseLogService exerciseLogService;

    public ExerciseLogController(ExerciseLogService exerciseLogService) {
        this.exerciseLogService = exerciseLogService;
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<ExerciseLogDto>> getExerciseLogsForUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(exerciseLogService.getLogsForUser(userId));
    }

    @PostMapping
    public ResponseEntity<FitForgeResponse<String>> createExerciseLog(
            @RequestBody CreateExerciseLogDto createDto) {
        FitForgeResponse<String> response = exerciseLogService.createLog(createDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Update an exercise log entry
     * PATCH /v1/{log_id}
     */
    @PatchMapping("/{logId}")
    public ResponseEntity<FitForgeResponse<String>> updateExerciseLog(
            @PathVariable("logId") Integer logId,
            @RequestBody ExerciseLogUpdateRequest updateRequest) {

        log.info("PATCH /v1/{}", logId);
        log.info("Update Request: {}", updateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new FitForgeResponse<>(exerciseLogService.updateLog(updateRequest, logId)));
    }

    /**
     * Delete an exercise log entry
     * DELETE /v1/{log_id}
     */
    @DeleteMapping("/{logId}")
    public ResponseEntity<Void> deleteExerciseLog(
            @RequestHeader("Authorization") String authToken,
            @PathVariable("logId") Integer logId) {
        log.info("DELETE /v1/{} (Auth: {})", logId, authToken);
        exerciseLogService.deleteLog(logId);
        return ResponseEntity.noContent().build();
    }
}