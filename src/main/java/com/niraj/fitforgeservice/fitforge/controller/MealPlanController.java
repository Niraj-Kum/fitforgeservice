package com.niraj.fitforgeservice.fitforge.controller;

import com.niraj.fitforgeservice.fitforge.dto.MealPlanGenerationRequest;
import com.niraj.fitforgeservice.fitforge.dto.MealPlanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1")
@Slf4j
public class MealPlanController {



    /**
     * Generate a new meal plan (AI)
     * POST /v1/meal-plans/generate
     */
    @PostMapping("/meal-plans/generate")
    public ResponseEntity<MealPlanResponse> generateMealPlan(
            @RequestHeader("Authorization") String authToken,
            @RequestBody MealPlanGenerationRequest generateRequest) {

        log.info("POST /v1/meal-plans/generate (Auth: {})", authToken);
        log.info("Generate Request: {}", generateRequest);

        MealPlanResponse generatedPlan = new MealPlanResponse(
                UUID.randomUUID().toString(),
                generateRequest.user_id(),
                "Personalized High-Protein Plan",
                "Breakfast: Oats with protein powder. Lunch: Chicken & veggies. Dinner: Salmon with rice.",
                LocalDateTime.now()
        );
        return ResponseEntity.ok(generatedPlan);
    }

    /**
     * Get user's saved meal plans
     * GET /v1/users/{user_id}/meal-plans
     */
    @GetMapping("/users/{userId}/meal-plans")
    public ResponseEntity<List<MealPlanResponse>> getUserSavedMealPlans(
            @RequestHeader("Authorization") String authToken,
            @PathVariable("userId") String userId) {

        log.info("GET /v1/users/{}/meal-plans (Auth: {})", userId, authToken);
        List<MealPlanResponse> savedPlans = Arrays.asList(
                new MealPlanResponse(UUID.randomUUID().toString(), userId, "Keto 7-Day Plan", "Keto details...", LocalDateTime.now().minusMonths(1)),
                new MealPlanResponse(UUID.randomUUID().toString(), userId, "Muscle Gain Plan", "Bulk recipes...", LocalDateTime.now().minusWeeks(2))
        );
        return ResponseEntity.ok(savedPlans);
    }
}