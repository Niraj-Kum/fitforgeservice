
package com.niraj.fitforgeservice.fitforge.repository;
import com.niraj.fitforgeservice.fitforge.dto.QuoteResponse;
import com.niraj.fitforgeservice.fitforge.entity.MotivationalQuote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MotivationalQuoteRepository extends JpaRepository<MotivationalQuote, Integer> {

    @Query(value = "SELECT mq.id, mq.quote, mq.author, mq.created_at FROM motivational_quotes mq ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<QuoteResponse> findRandomQuoteResponseNative();

    @Query(value = "SELECT new com.niraj.fitforgeservice.fitforge.dto.QuoteResponse(mq.id, mq.quote, mq.author, mq.createdAt) FROM MotivationalQuote mq ORDER BY mq.createdAt")
    List<QuoteResponse> getAllQuotes();

}
