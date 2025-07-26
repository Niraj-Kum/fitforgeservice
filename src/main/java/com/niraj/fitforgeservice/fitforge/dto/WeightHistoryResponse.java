package com.niraj.fitforgeservice.fitforge.dto;

import java.util.Date;

public record WeightHistoryResponse(Integer id, Integer userId, Date loggedAt,
                                    double weightLbs, Double bodyFatPct, Double muscleMassLbs) {

}