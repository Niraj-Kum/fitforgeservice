package com.niraj.fitforgeservice.fitforge.service;

import com.niraj.fitforgeservice.fitforge.dto.StrengthProgressDto;
import com.niraj.fitforgeservice.fitforge.entity.ExerciseLog;
import com.niraj.fitforgeservice.fitforge.repository.ExerciseLogRepository;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StrengthProgressService {

    private final ExerciseLogRepository exerciseLogRepository;

    public StrengthProgressService(ExerciseLogRepository exerciseLogRepository) {
        this.exerciseLogRepository = exerciseLogRepository;
    }

    public Map<String, List<StrengthProgressDto>> calculateStrengthProgress(Integer userId) {
        // 1. Fetch all logs for the user
        List<ExerciseLog> allLogs = exerciseLogRepository.getExerciseLogsByUserId(userId);

        if (allLogs.isEmpty()) {
            return Map.of();
        }

        // 2. Find the earliest log date to establish "Week 1"
        final var firstLogDate = allLogs.stream()
                .map(ExerciseLog::getCreatedAt)
                .min(Comparator.naturalOrder())
                .orElse(null);

        if (firstLogDate == null) return Map.of();
        ZoneId zoneId = ZoneId.systemDefault();

        // 3. Group logs by exercise name
        return allLogs.stream()
                .collect(Collectors.groupingBy(
                        ExerciseLog::getExerciseName,
                        // For each exercise, process its list of logs
                        Collectors.collectingAndThen(
                                // Group logs by week number
                                Collectors.groupingBy(
                                        log -> ChronoUnit.WEEKS.between(ZonedDateTime.ofInstant(firstLogDate.toInstant(), zoneId), ZonedDateTime.ofInstant(log.getCreatedAt().toInstant(), zoneId)) + 1,
                                        // For each week, find the log with the maximum weight
                                        Collectors.maxBy(Comparator.comparing(ExerciseLog::getWeightLbs))
                                ),
                                // After grouping, transform the map into the final DTO list
                                weeklyMaxes -> weeklyMaxes.entrySet().stream()
                                        .filter(entry -> entry.getValue().isPresent())
                                        .map(entry -> new StrengthProgressDto(
                                                "Week " + entry.getKey(),
                                                entry.getValue().get().getWeightLbs()
                                        ))
                                        .sorted(Comparator.comparing(dto -> Integer.parseInt(dto.getWeek().substring(5))))
                                        .collect(Collectors.toList())
                        )
                ));
    }
}