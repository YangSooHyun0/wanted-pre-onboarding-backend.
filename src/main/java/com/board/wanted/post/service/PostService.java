package com.board.wanted.post.service;

import com.board.wanted.post.dto.PostRequest;
import com.board.wanted.post.model.Post;
import com.board.wanted.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public Post createPost(PostRequest.PostDTO postDTO) {

        Post post = Post.builder()
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .build();

        return postRepository.save(post);
    }
}

