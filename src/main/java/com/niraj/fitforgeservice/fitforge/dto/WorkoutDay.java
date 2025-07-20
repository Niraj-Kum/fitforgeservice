package com.niraj.fitforgeservice.fitforge.dto;

import java.util.List;

public record WorkoutDay(int day_of_cycle, String title, String focus, String warmup, List<ExerciseCategory> strength) {

}