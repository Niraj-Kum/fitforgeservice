package com.niraj.fitforgeservice.fitforge.dto;

import java.util.List;

public record WorkoutPlanResponse(Integer id, Integer userId, String name, String startDate, boolean isActive, List<WorkoutDayData> days) {

}