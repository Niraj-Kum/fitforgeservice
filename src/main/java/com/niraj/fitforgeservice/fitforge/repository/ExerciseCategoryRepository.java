
package com.niraj.fitforgeservice.fitforge.repository;
import com.niraj.fitforgeservice.fitforge.entity.ExerciseCategory;
import com.niraj.fitforgeservice.fitforge.entity.WorkoutDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ExerciseCategoryRepository extends JpaRepository<ExerciseCategory, Integer> {

    @Query("SELECT e FROM ExerciseCategory e WHERE e.workoutDay.id IN :workoutDays")
    List<ExerciseCategory> getAllExerciseCategoryByWorkoutDays(Set<Integer> workoutDays);

}
