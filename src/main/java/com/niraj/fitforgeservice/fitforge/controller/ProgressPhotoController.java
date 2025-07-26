package com.niraj.fitforgeservice.fitforge.controller;

import com.niraj.fitforgeservice.fitforge.dto.ProgressPhotoResponse;
import com.niraj.fitforgeservice.fitforge.entity.ProgressPhoto;
import com.niraj.fitforgeservice.fitforge.service.ProgressPhotoService;
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
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1")
@Slf4j
public class ProgressPhotoController {

    private final ProgressPhotoService progressPhotoService;

    public ProgressPhotoController(ProgressPhotoService progressPhotoService) {
        this.progressPhotoService = progressPhotoService;
    }

    @GetMapping("/users/{userId}/progress-photos")
    public ResponseEntity<List<Map<String, String>>> getProgressPhotos(@PathVariable Integer userId) {
        List<ProgressPhoto> photos = progressPhotoService.getPhotosForUser(userId);

        List<Map<String, String>> response = photos.stream()
                .map(photo -> Map.of("image_url", photo.getImageUrl()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
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