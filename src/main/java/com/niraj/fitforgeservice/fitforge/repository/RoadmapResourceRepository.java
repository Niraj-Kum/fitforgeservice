package com.niraj.fitforgeservice.fitforge.repository;

import com.niraj.fitforgeservice.fitforge.entity.RoadmapResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoadmapResourceRepository extends JpaRepository<RoadmapResource, String> {
}