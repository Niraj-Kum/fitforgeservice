package com.niraj.fitforgeservice.fitforge.service;

import com.niraj.fitforgeservice.fitforge.dto.UpdateWorkoutSessionDto;
import com.niraj.fitforgeservice.fitforge.dto.WorkoutSessionDto;
import com.niraj.fitforgeservice.fitforge.dto.WorkoutSessionResponseDto;
import com.niraj.fitforgeservice.fitforge.entity.User;
import com.niraj.fitforgeservice.fitforge.entity.WorkoutPlan;
import com.niraj.fitforgeservice.fitforge.entity.WorkoutSession;
import com.niraj.fitforgeservice.fitforge.repository.WorkoutSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class WorkoutSessionService {

    private final WorkoutSessionRepository workoutSessionRepository;
    private final UserService userService;
    private final WorkoutPlanService workoutPlanService;

    public WorkoutSessionService(WorkoutSessionRepository workoutSessionRepository, UserService userService, WorkoutPlanService workoutPlanService) {
        this.workoutSessionRepository = workoutSessionRepository;
        this.userService = userService;
        this.workoutPlanService = workoutPlanService;
    }

    @Transactional
    public WorkoutSessionResponseDto createOrUpdateSession(Integer userId, UpdateWorkoutSessionDto dto) {
        Date today = new Date();

        // Find if a session for this user and date already exists
        Optional<WorkoutSession> existingSession = workoutSessionRepository.findByUserIdAndSessionDate(userId, today);

        WorkoutSession sessionToSave;
        if (existingSession.isPresent()) {
            // Update existing session
            sessionToSave = existingSession.get();
            sessionToSave.setDurationSeconds(dto.getDurationSeconds());
        } else {
            User user = userService.findUserById(userId);
            WorkoutPlan plan = workoutPlanService.findByUserIdAndPlanId(userId, dto.getPlanId());
            sessionToSave = new WorkoutSession();
            sessionToSave.setUser(user);
            sessionToSave.setWorkoutPlan(plan);
            sessionToSave.setSessionDate(today);
            sessionToSave.setDurationSeconds(dto.getDurationSeconds());
        }

        WorkoutSession savedSession = workoutSessionRepository.save(sessionToSave);
        return new WorkoutSessionResponseDto(savedSession.getId(), savedSession.getDurationSeconds());
    }

    @Transactional(readOnly = true)
    public Optional<WorkoutSessionDto> findTodaysSessionByUserId(Integer userId) {
        Date today = new Date();
        return workoutSessionRepository.findByUserIdAndSessionDate(userId, today)
                .map(this::convertToDto);
    }

    private WorkoutSessionDto convertToDto(WorkoutSession session) {
        WorkoutSessionDto dto = new WorkoutSessionDto();
        dto.setId(session.getId());
        dto.setUserId(session.getUser().getId());
        dto.setPlanId(session.getWorkoutPlan().getId());
        dto.setDurationSeconds(session.getDurationSeconds());
        return dto;
    }
}
