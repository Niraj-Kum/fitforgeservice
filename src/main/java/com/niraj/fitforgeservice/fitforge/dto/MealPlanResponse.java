package com.niraj.fitforgeservice.fitforge.dto;

import java.time.LocalDateTime;

public record MealPlanResponse(String id, String user_id, String name, String content, LocalDateTime generated_at) {

}
