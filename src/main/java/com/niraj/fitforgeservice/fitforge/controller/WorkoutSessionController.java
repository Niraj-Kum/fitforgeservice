package com.niraj.fitforgeservice.fitforge.controller;

import com.niraj.fitforgeservice.fitforge.dto.UpdateWorkoutSessionDto;
import com.niraj.fitforgeservice.fitforge.dto.WorkoutSessionDto;
import com.niraj.fitforgeservice.fitforge.dto.WorkoutSessionResponseDto;
import com.niraj.fitforgeservice.fitforge.service.WorkoutSessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/workout-sessions")
public class WorkoutSessionController {

    private final WorkoutSessionService workoutSessionService;

    public WorkoutSessionController(WorkoutSessionService workoutSessionService) {
        this.workoutSessionService = workoutSessionService;
    }

    @PostMapping("/users/{userId}/workout-sessions/today")
    public ResponseEntity<WorkoutSessionResponseDto> createOrUpdateWorkoutSession(
            @PathVariable Integer userId,
            @RequestBody UpdateWorkoutSessionDto updateDto) {

        WorkoutSessionResponseDto session = workoutSessionService.createOrUpdateSession(userId, updateDto);
        return new ResponseEntity<>(session, HttpStatus.OK);
    }

    /**
     * Retrieves the workout session for a specific user for the current day.
     * Returns a 404 Not Found if no session exists for today.
     * Corresponds to: GET /v1/users/{userId}/workout-sessions/today
     */
    @GetMapping("/users/{userId}/workout-sessions/today")
    public ResponseEntity<WorkoutSessionDto> getTodaysWorkoutSession(@PathVariable Integer userId) {
        Optional<WorkoutSessionDto> sessionDto = workoutSessionService.findTodaysSessionByUserId(userId);
        return sessionDto
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}