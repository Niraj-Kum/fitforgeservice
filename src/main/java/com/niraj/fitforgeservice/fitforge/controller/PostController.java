package com.niraj.fitforgeservice.fitforge.controller;

import com.niraj.fitforgeservice.fitforge.dto.PostCreateRequest;
import com.niraj.fitforgeservice.fitforge.dto.PostResponse;
import com.niraj.fitforgeservice.fitforge.dto.PostUpdateRequest;
import com.niraj.fitforgeservice.fitforge.exception.InvalidInputException;
import com.niraj.fitforgeservice.fitforge.exception.PostNotFoundException;
import com.niraj.fitforgeservice.fitforge.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/posts")
@Slf4j
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * Get all posts for the feed (paginated)
     * GET /v1/posts?page=1&limit=20
     */
    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(
            @RequestHeader("Authorization") String authToken,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {

        log.info("GET /v1/posts?page={}&limit={} (Auth: {})", page, limit, authToken);
        List<PostResponse> posts = postService.getAllPosts(page, limit);
        return ResponseEntity.ok(posts);
    }

    /**
     * Get a single post by ID
     * GET /v1/posts/{post_id}
     */
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPostById(
            @RequestHeader("Authorization") String authToken,
            @PathVariable("postId") Integer postId) {

        log.info("GET /v1/posts/{} (Auth: {})", postId, authToken);
        try {
            PostResponse post = postService.getPostById(postId);
            return ResponseEntity.ok(post);
        } catch (PostNotFoundException e) {
            log.warn("Post not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Or return an error DTO
        } catch (Exception e) {
            log.error("Error retrieving post {}: {}", postId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Create a new post
     * POST /v1/posts
     */
    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @RequestHeader("Authorization") String authToken,
            @RequestBody PostCreateRequest createRequest) {

        log.info("POST /v1/posts (Auth: {})", authToken);
        log.info("Create Request: {}", createRequest);
        try {
            PostResponse newPost = postService.createPost(createRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(newPost);
        } catch (InvalidInputException e) {
            log.warn("Invalid input for post creation: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Or return an error DTO
        } catch (Exception e) {
            log.error("Error creating post: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Update a post
     * PATCH /v1/posts/{post_id}
     */
    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePost(
            @RequestHeader("Authorization") String authToken,
            @PathVariable("postId") Integer postId,
            @RequestBody PostUpdateRequest updateRequest) {

        log.info("PATCH /v1/posts/{} (Auth: {})", postId, authToken);
        log.info("Update Request: {}", updateRequest);
        try {
            PostResponse updatedPost = postService.updatePost(postId, updateRequest);
            return ResponseEntity.ok(updatedPost);
        } catch (PostNotFoundException e) {
            log.warn("Post not found for update: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (InvalidInputException e) { // Could be thrown if e.g. content becomes empty after update
            log.warn("Invalid input for post update: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            log.error("Error updating post {}: {}", postId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Delete a post
     * DELETE /v1/posts/{post_id}
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @RequestHeader("Authorization") String authToken,
            @PathVariable("postId") Integer postId) {
        log.info("DELETE /v1/posts/{} (Auth: {})", postId, authToken);
        try {
            postService.deletePost(postId);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (PostNotFoundException e) {
            log.warn("Post not found for deletion: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error deleting post {}: {}", postId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}