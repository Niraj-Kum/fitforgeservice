package com.niraj.fitforgeservice.fitforge.dto;

import java.util.List;

public record WorkoutDayData(int dayOfCycle, String title, String focus, String warmup, List<ExerciseCategoryData> strength) {

}