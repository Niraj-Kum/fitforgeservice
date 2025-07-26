package com.niraj.fitforgeservice.fitforge.dto;

import java.util.Date;

public record ExerciseLogDto(Integer id, Integer userId, String createdAt, String exerciseName, Double weightLbs, Integer reps){

}
