package com.niraj.fitforgeservice.fitforge.dto;

import java.util.Date;

public record CreateWorkoutPlanRequest(String name, Date startDate, Boolean isPaused) {
}
