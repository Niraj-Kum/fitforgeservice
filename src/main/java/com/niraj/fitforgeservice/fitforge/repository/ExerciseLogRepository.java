package com.niraj.fitforgeservice.fitforge.repository;

import com.niraj.fitforgeservice.fitforge.dto.ExerciseLogResponse;
import com.niraj.fitforgeservice.fitforge.entity.ExerciseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseLogRepository extends JpaRepository<ExerciseLog, Integer> {

    @Query("SELECT new com.niraj.fitforgeservice.fitforge.dto.ExerciseLogResponse(e.id, e.user.id, e.exerciseName, e.weightLbs, e.reps, e.createdAt) FROM ExerciseLog e WHERE e.user.id = :userId")
    List<ExerciseLogResponse> getAllExcersiceLogs(Integer userId);

}
