package com.niraj.fitforgeservice.fitforge.service;

import com.niraj.fitforgeservice.fitforge.dto.*;
import com.niraj.fitforgeservice.fitforge.entity.ExerciseLog;
import com.niraj.fitforgeservice.fitforge.entity.User;
import com.niraj.fitforgeservice.fitforge.exception.InvalidInputException;
import com.niraj.fitforgeservice.fitforge.repository.ExerciseLogRepository;
import com.niraj.fitforgeservice.fitforge.utils.constants.ResponseMessages;
import com.niraj.fitforgeservice.fitforge.utils.helpers.DateHelpers;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExerciseLogService {

    private final ExerciseLogRepository exerciseLogRepository;
    private final UserService userService;

    public ExerciseLogService(ExerciseLogRepository exerciseLogRepository, UserService userService) {
        this.exerciseLogRepository = exerciseLogRepository;
        this.userService = userService;
    }

    public List<ExerciseLogDto> getLogsForUser(Integer userId) {
        List<ExerciseLog> exerciseLogs = exerciseLogRepository.getExerciseLogsByUserId(userId);
        if(exerciseLogs.isEmpty()) {
            return new ArrayList<>();
        }
        return exerciseLogs.stream().map(e -> new ExerciseLogDto(e.getId(), e.getUser().getId(), DateHelpers.getFormattedDateTime(e.getCreatedAt()),  e.getExerciseName(), e.getWeightLbs(), e.getReps())).toList();
    }

    public FitForgeResponse<String> createLog(CreateExerciseLogDto createRequest) {
        User user = userService.findUserById(createRequest.getUserId());
        ExerciseLog newLog = createRequest.exerciseLogMapper();
        newLog.setUser(user);
        ExerciseLog savedLog = exerciseLogRepository.save(newLog);
        return new FitForgeResponse<>(savedLog.getId(), ResponseMessages.CREATED_SUCCESSFULLY);
    }

    public String updateLog(ExerciseLogUpdateRequest updateRequest, Integer id) {
        ExerciseLog newLog = exerciseLogRepository.findById(id).orElseThrow(() -> new InvalidInputException("Log not Found"));
        if(Objects.nonNull(updateRequest.exerciseName())) {
            newLog.setExerciseName(updateRequest.exerciseName());
        }
        if(Objects.nonNull(updateRequest.weightLbs())) {
            newLog.setWeightLbs(updateRequest.weightLbs());
        }
        if(Objects.nonNull(updateRequest.reps())) {
            newLog.setReps(updateRequest.reps());
        }
        exerciseLogRepository.save(newLog);
        return ResponseMessages.UPDATED_SUCCESSFULLY;
    }

    public void deleteLog(Integer id) {
        ExerciseLog log = exerciseLogRepository.findById(id).orElseThrow(() -> new InvalidInputException("Log not Found"));
        exerciseLogRepository.delete(log);
    }
}