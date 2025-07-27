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

import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
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
        WorkoutSession sessionToSave = findSessionByUserId(userId);

        if (Objects.nonNull(sessionToSave)) {
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
    public WorkoutSessionDto findTodaysSessionByUserId(Integer userId) {
        return convertToDto(findSessionByUserId(userId));
    }

    private WorkoutSession findSessionByUserId(Integer userId) {
        ZoneId indianZone = ZoneId.of("Asia/Kolkata");
        LocalDate today = LocalDate.now();

        LocalDateTime startOfToday = today.atStartOfDay();

        LocalDateTime endOfToday = today.atTime(LocalTime.MAX);
        return workoutSessionRepository.findByUserIdAndSessionDateBetween(userId, Date.from(startOfToday.atZone(indianZone).toInstant()), Date.from(endOfToday.atZone(indianZone).toInstant())).orElse(null);
    }

    private WorkoutSessionDto convertToDto(WorkoutSession session) {
        WorkoutSessionDto dto = new WorkoutSessionDto();
        dto.setId(session.getId());
        dto.setUserId(session.getUser().getId());
        dto.setPlanId(session.getWorkoutPlan().getId());
        dto.setSessionDate(session.getSessionDate());
        dto.setDurationSeconds(session.getDurationSeconds());
        return dto;
    }
}
