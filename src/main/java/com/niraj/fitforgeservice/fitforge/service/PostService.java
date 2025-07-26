package com.niraj.fitforgeservice.fitforge.service;

import com.niraj.fitforgeservice.fitforge.dto.PostCreateRequest;
import com.niraj.fitforgeservice.fitforge.dto.PostResponse;
import com.niraj.fitforgeservice.fitforge.dto.PostUpdateRequest;
import com.niraj.fitforgeservice.fitforge.dto.UserLiteResponse;
import com.niraj.fitforgeservice.fitforge.entity.Post;
import com.niraj.fitforgeservice.fitforge.entity.User;
import com.niraj.fitforgeservice.fitforge.exception.InvalidInputException;
import com.niraj.fitforgeservice.fitforge.exception.PostNotFoundException;
import com.niraj.fitforgeservice.fitforge.repository.PostRepository;
import com.niraj.fitforgeservice.fitforge.repository.UserRepository;
import com.niraj.fitforgeservice.fitforge.utils.enums.PostTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    /**
     * Get all posts, with pagination.
     *
     * @param page  The page number (1-based).
     * @param limit The number of posts per page.
     * @return A list of PostResponse DTOs.
     */
    public List<PostResponse> getAllPosts(int page, int limit) {

        PageRequest pageRequest = PageRequest.of(page - 1, limit);
        Page<Post> postsPage = postRepository.findAll(pageRequest);
        return postsPage.getContent().stream()
                .map(this::mapToPostResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get a single post by ID.
     *
     * @param postId The ID of the post to retrieve.
     * @return The PostResponse DTO.
     * @throws PostNotFoundException if the post does not exist.
     */
    public PostResponse getPostById(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post with ID " + postId + " not found."));
        return mapToPostResponse(post);
    }

    /**
     * Create a new post.
     *
     * @param createRequest The DTO containing data for the new post.
     * @return The PostResponse DTO of the newly created post.
     * @throws InvalidInputException if required fields are missing or invalid.
     */
    public PostResponse createPost(PostCreateRequest createRequest) {

        if (createRequest.userId() == null) {
            throw new InvalidInputException("User ID is required for creating a post.");
        }
        if (createRequest.content() == null || createRequest.content().trim().isEmpty()) {
            throw new InvalidInputException("Content is required for creating a post.");
        }
        User user = userService.findUserById(createRequest.userId());
        Post newPost = new Post();
        newPost.setUser(user);
        newPost.setContent(createRequest.content());
        newPost.setImageUrl(createRequest.image_url());
        newPost.setPostType(createRequest.type());
        newPost.setCreatedAt(new Date());
        newPost.setUpdatedAt(new Date());

        Post savedPost = postRepository.save(newPost);
        return mapToPostResponse(savedPost);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByType(Integer type) {
        List<Post> posts;
        if (Objects.nonNull(type) && PostTypeEnum.BY_KEY.containsKey(type)) {
            posts = postRepository.findByPostTypeOrderByCreatedAtDesc(type);
        } else {
            posts = postRepository.findAllByOrderByCreatedAtDesc();
        }
        return posts.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private PostResponse convertToDto(Post post) {
        UserLiteResponse userDto = new UserLiteResponse();
        userDto.setId(post.getUser().getId());
        userDto.setName(post.getUser().getName());
        userDto.setAvatar(post.getUser().getAvatarUrl());
        return new PostResponse(post.getId(), userDto, post.getContent(), post.getImageUrl(), post.getPostType(), post.getLikes(), post.getComments(), post.getCreatedAt());
    }

    /**
     * Update an existing post.
     *
     * @param postId        The ID of the post to update.
     * @param updateRequest The DTO containing fields to update.
     * @return The PostResponse DTO of the updated post.
     * @throws PostNotFoundException if the post does not exist.
     */
    public PostResponse updatePost(Integer postId, PostUpdateRequest updateRequest) {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post with ID " + postId + " not found."));

        boolean isUpdated = false;
        if (updateRequest.content() != null && !updateRequest.content().trim().isEmpty()) {
            existingPost.setContent(updateRequest.content());
            isUpdated = true;
        }
        if (updateRequest.imageUrl() != null) {
            existingPost.setImageUrl(updateRequest.imageUrl());
            isUpdated = true;
        } else if (existingPost.getImageUrl() != null) {
            existingPost.setImageUrl(null);
            isUpdated = true;
        }

        if (isUpdated) {
            existingPost.setUpdatedAt(new Date());
            Post updatedPost = postRepository.save(existingPost);
            return mapToPostResponse(updatedPost);
        } else {
            return mapToPostResponse(existingPost);
        }
    }

    /**
     * Delete a post by ID.
     *
     * @param postId The ID of the post to delete.
     * @throws PostNotFoundException if the post does not exist.
     */
    public void deletePost(Integer postId) {
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException("Post with ID " + postId + " not found.");
        }
        postRepository.deleteById(postId);
    }


    private PostResponse mapToPostResponse(Post post) {
        if (post == null) {
            return null;
        }
        UserLiteResponse userLiteResponse = new UserLiteResponse(post.getUser().getId(),post.getUser().getName(), post.getUser().getAvatarUrl());
        return new PostResponse(
                post.getId(),
                userLiteResponse,
                post.getContent(),
                post.getImageUrl(),
                post.getPostType(),
                post.getLikes(),
                post.getComments(),
                post.getCreatedAt()
        );
    }
}