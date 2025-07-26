package com.niraj.fitforgeservice.fitforge.dto;

import java.util.Date;

public record UpdateWorkoutPlanDto(Boolean isActive, Date pauseDate, Date startDate) {
}
