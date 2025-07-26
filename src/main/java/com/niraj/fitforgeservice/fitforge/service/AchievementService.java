package com.niraj.fitforgeservice.fitforge.service;

import com.niraj.fitforgeservice.fitforge.dto.AchievementDto;
import com.niraj.fitforgeservice.fitforge.repository.ExerciseLogRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AchievementService {
    private final ExerciseLogRepository exerciseLogRepository;

    public AchievementService(ExerciseLogRepository exerciseLogRepository) {
        this.exerciseLogRepository = exerciseLogRepository;
    }

    public List<AchievementDto> getUserAchievements(Integer userId) {
        List<AchievementDto> allPossibleAchievements = loadAllAchievements();
        long workoutCount = exerciseLogRepository.countByUserId(userId);

        for (AchievementDto achievement : allPossibleAchievements) {
            if ("first-workout".equals(achievement.getId()) && workoutCount > 0) {
                achievement.setUnlocked(true);
                achievement.setDate(new Date());
            }
            if ("tenth-workout".equals(achievement.getId()) && workoutCount >= 10) {
                achievement.setUnlocked(true);
                achievement.setDate(new Date());
            }
        }

        return allPossibleAchievements;
    }

    private List<AchievementDto> loadAllAchievements() {
        List<AchievementDto> list = new ArrayList<>();
        return list;
    }
}
