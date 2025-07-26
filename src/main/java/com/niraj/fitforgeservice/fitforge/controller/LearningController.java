package com.niraj.fitforgeservice.fitforge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/v1/learning")
public class LearningController {

    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;

    public LearningController(ResourceLoader resourceLoader, ObjectMapper objectMapper) {
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/roadmap")
    public ResponseEntity<Object> getRoadmap() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:data/tech-learning-roadmap.json");
        Object roadmapData = objectMapper.readValue(resource.getInputStream(), Object.class);
        return ResponseEntity.ok(roadmapData);
    }
}