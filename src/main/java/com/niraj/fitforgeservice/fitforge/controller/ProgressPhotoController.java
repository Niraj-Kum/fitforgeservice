package com.niraj.fitforgeservice.fitforge.controller;

import com.niraj.fitforgeservice.fitforge.dto.ProgressPhotoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1")
@Slf4j
public class ProgressPhotoController {

    /**
     * Get all progress photos for a user
     * GET /v1/users/{user_id}/progress-photos
     */
    @GetMapping("/users/{userId}/progress-photos")
    public ResponseEntity<List<ProgressPhotoResponse>> getUserProgressPhotos(
            @RequestHeader("Authorization") String authToken,
            @PathVariable("userId") String userId) {

        log.info("GET /v1/users/{}/progress-photos (Auth: {})", userId, authToken);
        List<ProgressPhotoResponse> photos = Arrays.asList(
                new ProgressPhotoResponse(UUID.randomUUID().toString(), userId, "http://example.com/photo1.jpg", "Start of journey", LocalDate.of(2024, 1, 1), LocalDateTime.now().minusMonths(6)),
                new ProgressPhotoResponse(UUID.randomUUID().toString(), userId, "http://example.com/photo2.jpg", "Week 4 progress!", LocalDate.of(2024, 10, 26), LocalDateTime.now())
        );
        return ResponseEntity.ok(photos);
    }

    /**
     * Upload a new progress photo
     * POST /v1/progress-photos
     * Uses multipart/form-data.
     */
    @PostMapping(path = "/progress-photos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProgressPhotoResponse> uploadProgressPhoto(
            @RequestHeader("Authorization") String authToken,
            @RequestPart("image") MultipartFile image,
            @RequestParam("user_id") String user_id,
            @RequestParam(value = "caption", required = false) String caption,
            @RequestParam(value = "taken_on", required = false) LocalDate taken_on) {


        log.info("POST /v1/progress-photos (Auth: {})", authToken);
        log.info("Received file: {}, size: {}", image.getOriginalFilename(), image.getSize());
        log.info("User ID: {}", user_id);
        log.info("Caption: {}", caption);
        log.info("Taken On: {}", taken_on);

        String imageUrl = "https://cdn.example.com/uploaded/" + UUID.randomUUID().toString() + ".jpg";
        ProgressPhotoResponse newPhoto = new ProgressPhotoResponse(
                UUID.randomUUID().toString(),
                user_id,
                imageUrl,
                caption,
                taken_on != null ? taken_on : LocalDate.now(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(newPhoto);
    }

    /**
     * Delete a progress photo
     * DELETE /v1/progress-photos/{photo_id}
     */
    @DeleteMapping("/progress-photos/{photoId}")
    public ResponseEntity<Void> deleteProgressPhoto(
            @RequestHeader("Authorization") String authToken,
            @PathVariable("photoId") String photoId) {
        log.info("DELETE /v1/progress-photos/{} (Auth: {})", photoId, authToken);
        return ResponseEntity.noContent().build();
    }
}