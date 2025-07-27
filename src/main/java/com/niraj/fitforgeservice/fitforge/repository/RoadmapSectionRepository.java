package com.niraj.fitforgeservice.fitforge.repository;

import com.niraj.fitforgeservice.fitforge.entity.RoadmapSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoadmapSectionRepository extends JpaRepository<RoadmapSection, String> {
    List<RoadmapSection> findAllByOrderByDisplayOrder();
}
