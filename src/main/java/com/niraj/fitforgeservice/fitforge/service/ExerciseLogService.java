package com.niraj.fitforgeservice.fitforge.service;

import com.niraj.fitforgeservice.fitforge.dto.ExerciseLogCreateRequest;
import com.niraj.fitforgeservice.fitforge.dto.ExerciseLogResponse;
import com.niraj.fitforgeservice.fitforge.dto.ExerciseLogUpdateRequest;
import com.niraj.fitforgeservice.fitforge.entity.ExerciseLog;
import com.niraj.fitforgeservice.fitforge.entity.User;
import com.niraj.fitforgeservice.fitforge.exception.InvalidInputException;
import com.niraj.fitforgeservice.fitforge.repository.ExerciseLogRepository;
import com.niraj.fitforgeservice.fitforge.utils.constants.ResponseMessages;
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

    public Map<String , List<ExerciseLogResponse>> getExerciseLogsByUserId(Integer userId) {
        List<ExerciseLogResponse> logs = exerciseLogRepository.getAllExcersiceLogs(userId);
        if(logs.isEmpty()) {
            return new HashMap<>();
        }
        return logs.stream().collect(Collectors.groupingBy(ExerciseLogResponse::exerciseName));
    }

    public String createLog(ExerciseLogCreateRequest createRequest) {
        User user = userService.findUserById(createRequest.userId());
        ExerciseLog newLog = createRequest.exerciseLogMapper();
        newLog.setUser(user);
        exerciseLogRepository.save(newLog);
        return ResponseMessages.CREATED_SUCCESSFULLY;
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