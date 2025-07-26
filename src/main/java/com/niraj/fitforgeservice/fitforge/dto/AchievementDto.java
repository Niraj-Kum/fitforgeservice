package com.niraj.fitforgeservice.fitforge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AchievementDto {
    private String id;
    private String title;
    private String description;
    private String icon;
    private boolean unlocked;
    private Date date;
}
