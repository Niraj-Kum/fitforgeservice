package com.niraj.fitforgeservice.fitforge.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoadmapPhaseDto {
    private Integer id;
    private String name;
    private List<RoadmapTopicDto> topics;
}
