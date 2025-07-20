package com.niraj.fitforgeservice.fitforge.dto;

import java.util.Date;

public record QuoteResponse(Integer id, String quote, String author, Date createdAt) {

}