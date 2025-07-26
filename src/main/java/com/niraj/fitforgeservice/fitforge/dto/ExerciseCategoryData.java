package com.niraj.fitforgeservice.fitforge.dto;

import java.util.List;

public record ExerciseCategoryData(String name, List<ExerciseData> exercises) {

}