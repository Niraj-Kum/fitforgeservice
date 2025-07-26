
package com.niraj.fitforgeservice.fitforge.repository;
import com.niraj.fitforgeservice.fitforge.entity.WeightHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeightHistoryRepository extends JpaRepository<WeightHistory, Integer> {
    List<WeightHistory> findAllByUserIdOrderByLoggedAtDesc(Integer userId);
}
