
package com.niraj.fitforgeservice.fitforge.repository;
import com.niraj.fitforgeservice.fitforge.entity.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Integer> {

    @Query("SELECT wp FROM WorkoutPlan wp WHERE wp.user.id = :userId")
    Optional<WorkoutPlan> findByUserId(Integer userId);

    @Query("SELECT wp FROM WorkoutPlan wp WHERE wp.user.id = :userId AND wp.id = :planId")
    Optional<WorkoutPlan> findByUserIdAndPlanId(Integer userId, Integer planId);
}
