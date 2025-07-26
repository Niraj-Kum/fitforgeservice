package com.niraj.fitforgeservice.fitforge.dto;

import com.niraj.fitforgeservice.fitforge.entity.ExerciseLog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateExerciseLogDto {

    private Integer id;
    private Integer userId;
    private String exerciseName;
    private Double weightLbs;
    private Integer reps;

    public CreateExerciseLogDto(Integer userId, String exerciseName, Double weightLbs, Integer reps) {
        this.userId = userId;
        this.exerciseName = exerciseName;
        this.weightLbs = weightLbs;
        this.reps = reps;
    }

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
