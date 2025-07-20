package com.niraj.fitforgeservice.fitforge.dto;

import com.niraj.fitforgeservice.fitforge.entity.MotivationalQuote;
import io.micrometer.common.util.StringUtils;

public record QuoteCreateRequest(String quote, String author) {
    public MotivationalQuote createQuote() {
        MotivationalQuote motivationalQuote = new MotivationalQuote();
        motivationalQuote.setQuote(quote);
        if(StringUtils.isNotBlank(author)) {
            motivationalQuote.setAuthor(author);
        }
        return motivationalQuote;
    }
}