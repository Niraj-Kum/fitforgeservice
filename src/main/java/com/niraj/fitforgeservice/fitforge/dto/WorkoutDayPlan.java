package com.niraj.fitforgeservice.fitforge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WorkoutDayPlan {
     private Integer day;
     private String title;
     private String focus;
     private String warmup;
     private List<ExerciseCategoryData> strength;
     private String cardio;
     private List<ExerciseData> abs;
     private List<ExerciseData> neck;
     private List<ExerciseData> calisthenics;
     private List<ExerciseData> functionalCore;
     private String activeRecovery;
}
