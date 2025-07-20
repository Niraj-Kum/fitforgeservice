
package com.niraj.fitforgeservice.fitforge.repository;
import com.niraj.fitforgeservice.fitforge.entity.WeightHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeightHistoryRepository extends JpaRepository<WeightHistory, Integer> {

}
