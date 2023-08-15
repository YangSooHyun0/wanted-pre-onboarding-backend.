package com.board.wanted.post.controller;

import com.board.wanted.post.dto.PostRequest;
import com.board.wanted.post.model.Post;
import com.board.wanted.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestBody @Valid PostRequest.PostDTO postDTO) {

        log.info("Request to create post: {}", postDTO);

        return ResponseEntity.ok(postService.createPost(postDTO));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<Post>> getPosts(Pageable pageable) {

        log.info("Request to fetch posts with pagination: {}", pageable);

        return ResponseEntity.ok(postService.getPosts(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {

        log.info("Request to fetch post with ID: {}", id);

        return ResponseEntity.ok(postService.getPostById(id));
    }
}