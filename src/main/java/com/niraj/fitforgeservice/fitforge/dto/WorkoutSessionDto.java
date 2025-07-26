package com.niraj.fitforgeservice.fitforge.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class WorkoutSessionDto {
    private Integer id;
    private Integer userId;
    private Integer planId;
    private Date sessionDate;
    private Integer durationSeconds;
}
