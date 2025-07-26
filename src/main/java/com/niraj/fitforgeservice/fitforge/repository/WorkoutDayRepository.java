
package com.niraj.fitforgeservice.fitforge.repository;
import com.niraj.fitforgeservice.fitforge.entity.WorkoutDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutDayRepository extends JpaRepository<WorkoutDay, Integer> {

    @Query("SELECT wd FROM WorkoutDay wd WHERE wd.workoutPlan.id = :workoutPlanId")
    List<WorkoutDay> findAllByWorkoutPlanId(Integer workoutPlanId);
}
