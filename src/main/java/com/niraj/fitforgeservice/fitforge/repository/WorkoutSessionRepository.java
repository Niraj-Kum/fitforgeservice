package com.niraj.fitforgeservice.fitforge.repository;

import com.niraj.fitforgeservice.fitforge.entity.WorkoutSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession, String> {
    Optional<WorkoutSession> findByUserIdAndSessionDate(Integer userId, Date sessionDate);
}
