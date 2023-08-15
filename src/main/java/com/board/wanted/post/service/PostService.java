package com.board.wanted.post.service;

import com.board.wanted._core.errors.ErrorMessage;
import com.board.wanted._core.errors.exception.Exception403;
import com.board.wanted._core.errors.exception.Exception404;
import com.board.wanted.post.dto.PostRequest;
import com.board.wanted.post.model.Post;
import com.board.wanted.post.repository.PostRepository;
import com.board.wanted.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Post createPost(PostRequest.PostDTO postDTO, User currentUser) {

        Post post = Post.builder()
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .user(currentUser)
                .build();

        return postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public Page<Post> getPosts(Pageable pageable) {

        return postRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Post getPostById(Long id) {

        return postRepository.findById(id)
                .orElseThrow(() -> new Exception404(ErrorMessage.POST_NOT_FOUND));
    }

    @Transactional
    public Post updatePost(Long id, PostRequest.PostDTO postDTO, User currentUser) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new Exception404(ErrorMessage.POST_NOT_FOUND));

        if (!post.getUser().getId().equals(currentUser.getId())) {
            throw new Exception403(ErrorMessage.FORBIDDEN);
        }

        post.updateContent(postDTO.getTitle(), postDTO.getContent());
        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long id, User currentUser) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new Exception404(ErrorMessage.POST_NOT_FOUND));

        if (!post.getUser().getId().equals(currentUser.getId())) {
            throw new Exception403(ErrorMessage.FORBIDDEN);
        }

        postRepository.delete(post);
    }
}