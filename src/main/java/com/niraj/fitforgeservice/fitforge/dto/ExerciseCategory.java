package com.niraj.fitforgeservice.fitforge.dto;

import java.util.List;

public record ExerciseCategory(String name, List<Exercise> exercises) {

}