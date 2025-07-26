package com.niraj.fitforgeservice.fitforge.dto;

import com.niraj.fitforgeservice.fitforge.entity.WorkoutDay;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

public record CreateWorkoutPlanRequest(String name, Date startDate, Boolean isPaused) {
}
