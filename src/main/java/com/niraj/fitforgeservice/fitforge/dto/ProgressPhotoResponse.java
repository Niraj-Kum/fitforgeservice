package com.niraj.fitforgeservice.fitforge.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ProgressPhotoResponse(String id, String user_id, String image_url, String caption, LocalDate taken_on, LocalDateTime uploaded_at) {

}
