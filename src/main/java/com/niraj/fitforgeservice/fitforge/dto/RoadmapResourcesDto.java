package com.niraj.fitforgeservice.fitforge.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoadmapResourcesDto {
    private List<String> platforms;
    private List<String> books;
    private List<LinkDto> courses;
    private List<LinkDto> docs;
}