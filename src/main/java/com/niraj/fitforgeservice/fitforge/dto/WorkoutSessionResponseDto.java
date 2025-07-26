package com.niraj.fitforgeservice.fitforge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WorkoutSessionResponseDto {
    private Integer id;
    private Integer durationSeconds;
}
