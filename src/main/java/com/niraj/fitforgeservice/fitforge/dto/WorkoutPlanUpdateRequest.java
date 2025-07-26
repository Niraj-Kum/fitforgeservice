package com.niraj.fitforgeservice.fitforge.dto;

import java.util.List;

public record WorkoutPlanUpdateRequest(String name, Boolean is_active, List<WorkoutDayData> days) {

}