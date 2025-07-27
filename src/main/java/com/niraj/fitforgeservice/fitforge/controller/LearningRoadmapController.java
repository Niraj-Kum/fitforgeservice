package com.niraj.fitforgeservice.fitforge.controller;

import com.niraj.fitforgeservice.fitforge.dto.RoadmapSectionDto;
import com.niraj.fitforgeservice.fitforge.service.LearningRoadmapService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/learning")
public class LearningRoadmapController {

    private final LearningRoadmapService learningRoadmapService;

    public LearningRoadmapController(LearningRoadmapService learningRoadmapService) {
        this.learningRoadmapService = learningRoadmapService;
    }

    /**
     * Retrieves the entire, structured learning roadmap.
     * Corresponds to: GET /v1/learning/roadmap
     */
    @GetMapping("/roadmap")
    public ResponseEntity<List<RoadmapSectionDto>> getFullRoadmap() {
        List<RoadmapSectionDto> roadmap = learningRoadmapService.getFullRoadmap();
        return ResponseEntity.ok(roadmap);
    }
}