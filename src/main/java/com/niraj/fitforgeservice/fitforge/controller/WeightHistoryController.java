package com.niraj.fitforgeservice.fitforge.controller;

import com.niraj.fitforgeservice.fitforge.dto.WeightHistoryCreateRequest;
import com.niraj.fitforgeservice.fitforge.dto.WeightHistoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1")
@Slf4j
public class WeightHistoryController {

    /**
     * Get a user's weight history
     * GET /v1/users/{user_id}/weight-history
     */
    @GetMapping("/users/{userId}/weight-history")
    public ResponseEntity<List<WeightHistoryResponse>> getUserWeightHistory(
            @RequestHeader("Authorization") String authToken,
            @PathVariable("userId") String userId) {

        log.info("GET /v1/users/{}/weight-history (Auth: {})", userId, authToken);
        List<WeightHistoryResponse> history = Arrays.asList(
                new WeightHistoryResponse(UUID.randomUUID().toString(), userId, 185.0, 20.0, 80.0, LocalDate.of(2024, 9, 1)),
                new WeightHistoryResponse(UUID.randomUUID().toString(), userId, 182.5, 18.9, 81.8, LocalDate.of(2024, 10, 26))
        );
        return ResponseEntity.ok(history);
    }

    /**
     * Add a new weight history entry
     * POST /v1/weight-history
     */
    @PostMapping("/weight-history")
    public ResponseEntity<WeightHistoryResponse> addWeightHistoryEntry(
            @RequestHeader("Authorization") String authToken,
            @RequestBody WeightHistoryCreateRequest createRequest) {

        log.info("POST /v1/weight-history (Auth: {})", authToken);
        log.info("Create Request: {}", createRequest);
        WeightHistoryResponse newEntry = new WeightHistoryResponse(
                UUID.randomUUID().toString(),
                createRequest.user_id(),
                createRequest.weight_lbs(),
                createRequest.body_fat_pct(),
                createRequest.muscle_mass_lbs(),
                createRequest.logged_at() != null ? createRequest.logged_at() : LocalDate.now()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(newEntry);
    }
}