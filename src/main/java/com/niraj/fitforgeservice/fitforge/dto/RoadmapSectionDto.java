package com.niraj.fitforgeservice.fitforge.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoadmapSectionDto {
    private Integer id;
    private String title;
    private String icon;
    private List<RoadmapPhaseDto> phases;
}
