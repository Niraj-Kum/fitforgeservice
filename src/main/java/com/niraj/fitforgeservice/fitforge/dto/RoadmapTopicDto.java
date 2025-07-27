package com.niraj.fitforgeservice.fitforge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoadmapTopicDto {
    private Integer id;
    private String name;
    private String description;
    private RoadmapResourcesDto resources;
}