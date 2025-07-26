package com.niraj.fitforgeservice.fitforge.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserWorkoutPlan {
    private List<WorkoutDayPlan> plan;
    private String startDate;
    private Boolean isPaused;
    private String pauseDate;
}