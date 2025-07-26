package com.niraj.fitforgeservice.fitforge.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CreateWeightHistoryDto {
    private Integer userId;
    private Double weightLbs;
    private Double bodyFatPct;
    private Double muscleMassLbs;
    private Date loggedAt;
}