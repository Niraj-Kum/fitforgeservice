package com.niraj.fitforgeservice.fitforge.dto;

import java.util.Date;

public record ExerciseLogResponse(Integer id, Integer userId, String exerciseName, Double weightLbs, Integer reps,
                                  Date loggedAt) {

}