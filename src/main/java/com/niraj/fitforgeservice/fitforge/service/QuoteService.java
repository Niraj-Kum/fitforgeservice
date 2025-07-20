package com.niraj.fitforgeservice.fitforge.service;

import com.niraj.fitforgeservice.fitforge.dto.QuoteCreateRequest;
import com.niraj.fitforgeservice.fitforge.dto.QuoteResponse;
import com.niraj.fitforgeservice.fitforge.entity.MotivationalQuote;
import com.niraj.fitforgeservice.fitforge.repository.MotivationalQuoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class QuoteService {

    private final MotivationalQuoteRepository motivationalQuoteRepository;

    public QuoteService(MotivationalQuoteRepository motivationalQuoteRepository) {
        this.motivationalQuoteRepository = motivationalQuoteRepository;
    }


    public QuoteResponse getRandomMotivationalQuote() {

        return motivationalQuoteRepository.findRandomQuoteResponseNative().orElse(null);
    }

    public List<QuoteResponse> getAllMotivationalQuotes() {

        return motivationalQuoteRepository.getAllQuotes();
    }

    public QuoteResponse createMotivationalQuote(
            String adminAuthToken,
            QuoteCreateRequest createRequest) {

        MotivationalQuote savedQuote = motivationalQuoteRepository.save(createRequest.createQuote());
        return new QuoteResponse(savedQuote.getId(), savedQuote.getQuote(), savedQuote.getAuthor(), savedQuote.getCreatedAt());
    }
}