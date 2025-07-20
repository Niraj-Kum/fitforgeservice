
package com.niraj.fitforgeservice.fitforge.repository;
import com.niraj.fitforgeservice.fitforge.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
