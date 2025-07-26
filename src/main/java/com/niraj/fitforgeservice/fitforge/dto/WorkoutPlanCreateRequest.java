package com.niraj.fitforgeservice.fitforge.dto;

import java.time.LocalDate;
import java.util.List;

public record WorkoutPlanCreateRequest(String user_id, String name, LocalDate start_date, boolean is_active, List<WorkoutDayData> days) {

}