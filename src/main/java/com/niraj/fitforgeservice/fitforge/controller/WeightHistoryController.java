package com.niraj.fitforgeservice.fitforge.controller;

import com.niraj.fitforgeservice.fitforge.dto.CreateWeightHistoryDto;
import com.niraj.fitforgeservice.fitforge.dto.WeightHistoryCreateRequest;
import com.niraj.fitforgeservice.fitforge.dto.WeightHistoryResponse;
import com.niraj.fitforgeservice.fitforge.service.WeightHistoryService;
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

    private final WeightHistoryService weightHistoryService;

    public WeightHistoryController(WeightHistoryService weightHistoryService) {
        this.weightHistoryService = weightHistoryService;
    }

    /**
     * Retrieves all weight history entries for a specific user.
     * Corresponds to: GET /v1/users/{userId}/weight-history
     */
    @GetMapping("/users/{userId}/weight-history")
    public ResponseEntity<List<WeightHistoryResponse>> getWeightHistory(@PathVariable Integer userId) {
        List<WeightHistoryResponse> history = weightHistoryService.getHistoryForUser(userId);
        return ResponseEntity.ok(history);
    }

    /**
     * Creates a new weight history entry.
     * Corresponds to: POST /v1/weight-history
     */
    @PostMapping("/weight-history")
    public ResponseEntity<WeightHistoryResponse> addWeightHistoryEntry(@RequestBody CreateWeightHistoryDto createDto) {
        WeightHistoryResponse newEntry = weightHistoryService.createEntry(createDto);
        return new ResponseEntity<>(newEntry, HttpStatus.CREATED);
    }
}