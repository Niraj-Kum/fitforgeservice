package com.niraj.fitforgeservice.fitforge.service;

import com.niraj.fitforgeservice.fitforge.dto.*;
import com.niraj.fitforgeservice.fitforge.entity.RoadmapPhase;
import com.niraj.fitforgeservice.fitforge.entity.RoadmapResource;
import com.niraj.fitforgeservice.fitforge.entity.RoadmapSection;
import com.niraj.fitforgeservice.fitforge.entity.RoadmapTopic;
import com.niraj.fitforgeservice.fitforge.repository.RoadmapPhaseRepository;
import com.niraj.fitforgeservice.fitforge.repository.RoadmapResourceRepository;
import com.niraj.fitforgeservice.fitforge.repository.RoadmapSectionRepository;
import com.niraj.fitforgeservice.fitforge.repository.RoadmapTopicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class LearningRoadmapService {

    private final RoadmapSectionRepository sectionRepository;
    private final RoadmapPhaseRepository phaseRepository;
    private final RoadmapTopicRepository topicRepository;
    private final RoadmapResourceRepository resourceRepository;

    public LearningRoadmapService(RoadmapSectionRepository sectionRepository, RoadmapPhaseRepository phaseRepository, RoadmapTopicRepository topicRepository, RoadmapResourceRepository resourceRepository) {
        this.sectionRepository = sectionRepository;
        this.phaseRepository = phaseRepository;
        this.topicRepository = topicRepository;
        this.resourceRepository = resourceRepository;
    }

    @Transactional(readOnly = true)
    public List<RoadmapSectionDto> getFullRoadmap() {
        // Fetch all data in bulk to minimize database queries
        List<RoadmapSection> sections = sectionRepository.findAllByOrderByDisplayOrder();
        List<RoadmapPhase> phases = phaseRepository.findAll();
        List<RoadmapTopic> topics = topicRepository.findAll();
        List<RoadmapResource> resources = resourceRepository.findAll();

        // Group children by their parent IDs
        Map<Integer, List<RoadmapPhase>> phasesBySectionId = phases.stream().collect(groupingBy(rp -> rp.getSection().getId()));
        Map<Integer, List<RoadmapTopic>> topicsByPhaseId = topics.stream().collect(groupingBy(rt ->rt.getPhase().getId()));
        Map<Integer, List<RoadmapResource>> resourcesByTopicId = resources.stream().collect(groupingBy(rr -> rr.getTopic().getId()));

        // Assemble the DTOs
        return sections.stream().map(section -> {
            RoadmapSectionDto sectionDto = new RoadmapSectionDto();
            sectionDto.setId(section.getId());
            sectionDto.setTitle(section.getTitle());
            sectionDto.setIcon(section.getIcon());

            List<RoadmapPhase> sectionPhases = phasesBySectionId.getOrDefault(section.getId(), new ArrayList<>());
            sectionDto.setPhases(sectionPhases.stream()
                    .sorted(Comparator.comparing(RoadmapPhase::getDisplayOrder))
                    .map(phase -> {
                        RoadmapPhaseDto phaseDto = new RoadmapPhaseDto();
                        phaseDto.setId(phase.getId());
                        phaseDto.setName(phase.getName());

                        List<RoadmapTopic> phaseTopics = topicsByPhaseId.getOrDefault(phase.getId(), new ArrayList<>());
                        phaseDto.setTopics(phaseTopics.stream()
                                .sorted(Comparator.comparing(RoadmapTopic::getDisplayOrder))
                                .map(topic -> {
                                    RoadmapTopicDto topicDto = new RoadmapTopicDto();
                                    topicDto.setId(topic.getId());
                                    topicDto.setName(topic.getName());
                                    topicDto.setDescription(topic.getDescription());

                                    List<RoadmapResource> topicResources = resourcesByTopicId.getOrDefault(topic.getId(), new ArrayList<>());
                                    topicDto.setResources(convertToResourcesDto(topicResources));

                                    return topicDto;
                                }).collect(Collectors.toList()));
                        return phaseDto;
                    }).collect(Collectors.toList()));
            return sectionDto;
        }).collect(Collectors.toList());
    }

    private RoadmapResourcesDto convertToResourcesDto(List<RoadmapResource> resources) {
        if (resources == null || resources.isEmpty()) {
            return null;
        }

        RoadmapResourcesDto dto = new RoadmapResourcesDto();
        dto.setPlatforms(filterResources(resources, "platform", RoadmapResource::getName));
        dto.setBooks(filterResources(resources, "book", RoadmapResource::getName));
        dto.setCourses(filterResources(resources, "course", r -> new LinkDto(r.getName(), r.getUrl())));
        dto.setDocs(filterResources(resources, "doc", r -> new LinkDto(r.getName(), r.getUrl())));
        return dto;
    }

    private <T> List<T> filterResources(List<RoadmapResource> resources, String type, java.util.function.Function<RoadmapResource, T> mapper) {
        return resources.stream()
                .filter(r -> type.equals(r.getType()))
                .map(mapper)
                .collect(Collectors.toList());
    }
}