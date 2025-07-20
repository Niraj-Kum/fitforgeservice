package com.niraj.fitforgeservice.fitforge.controller;

import com.niraj.fitforgeservice.fitforge.dto.QuoteCreateRequest;
import com.niraj.fitforgeservice.fitforge.dto.QuoteResponse;
import com.niraj.fitforgeservice.fitforge.service.QuoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/quotes")
@Slf4j
public class QuoteController {

    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    /**
     * Get a random motivational quote
     * GET /v1/quotes/random
     */
    @GetMapping("/random")
    public ResponseEntity<QuoteResponse> getRandomMotivationalQuote() {

        log.info("GET /v1/quotes/random");
        return ResponseEntity.ok(quoteService.getRandomMotivationalQuote());
    }

    /**
     * Get all motivational quotes
     * GET /v1/quotes
     */
    @GetMapping
    public ResponseEntity<List<QuoteResponse>> getAllMotivationalQuotes() {

        log.info("GET /v1/quotes");
        return ResponseEntity.ok(quoteService.getAllMotivationalQuotes());
    }

    /**
     * Create a new motivational quote (Admin only)
     * POST /v1/quotes
     */
    @PostMapping
    public ResponseEntity<QuoteResponse> createMotivationalQuote(
            @RequestHeader("Authorization") String adminAuthToken,
            @RequestBody QuoteCreateRequest createRequest) {

        log.info("POST /v1/quotes (Admin Auth: {})", adminAuthToken);
        log.info("Create Request: {}", createRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(quoteService.createMotivationalQuote(adminAuthToken, createRequest));
    }
}