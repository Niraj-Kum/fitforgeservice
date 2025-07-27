package com.niraj.fitforgeservice.fitforge.repository;

import com.niraj.fitforgeservice.fitforge.entity.WorkoutSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession, String> {
    @Query("SELECT w FROM WorkoutSession w WHERE w.user.id = :userId AND w.sessionDate BETWEEN :startDate AND :endDate")
    Optional<WorkoutSession> findByUserIdAndSessionDateBetween(Integer userId, Date startDate, Date endDate);
}
