
package com.niraj.fitforgeservice.fitforge.repository;
import com.niraj.fitforgeservice.fitforge.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {

}
