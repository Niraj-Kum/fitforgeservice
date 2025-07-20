package com.niraj.fitforgeservice.fitforge.dto;

import com.niraj.fitforgeservice.fitforge.entity.ExerciseLog;

import java.util.Objects;

public record ExerciseLogCreateRequest(Integer id, Integer userId, String exerciseName, double weightLbs, int reps) {
    public ExerciseLog exerciseLogMapper() {
        ExerciseLog exerciseLog = new ExerciseLog();
        if(Objects.nonNull(this.id)) {
            exerciseLog.setId(this.id);
        }
        exerciseLog.setWeightLbs(this.weightLbs);
        exerciseLog.setReps(this.reps);
        exerciseLog.setExerciseName(this.exerciseName);
        return exerciseLog;
    }
}