package com.niraj.fitforgeservice.fitforge.dto;

import java.time.LocalDate;

public record WeightHistoryResponse(String id, String user_id, double weight_lbs, Double body_fat_pct, Double muscle_mass_lbs,
                                    LocalDate logged_at) {

}