package com.board.wanted;

import com.board.wanted._core.errors.exception.Exception403;
import com.board.wanted._core.errors.exception.Exception404;
import com.board.wanted.post.dto.PostRequest;
import com.board.wanted.post.model.Post;
import com.board.wanted.post.repository.PostRepository;
import com.board.wanted.post.service.PostService;
import com.board.wanted.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Nested
    @DisplayName("게시글 생성 서비스 단위 테스트")
    class CreatePost {

        @DisplayName("성공")
        @Test
        void success_Test() {
            // Given
            PostRequest.PostDTO postDTO = PostRequest.PostDTO.builder()
                    .title("testTitle")
                    .content("testContent")
                    .build();

            User currentUser = User.builder().id(1L).build();

            Mockito.when(postRepository.save(ArgumentMatchers.any(Post.class)))
                    .then(invocation -> invocation.getArgument(0));

            // When
            Post result = postService.createPost(postDTO, currentUser);

            // Then
            Assertions.assertAll(
                    () -> Assertions.assertEquals("testTitle", result.getTitle()),
                    () -> Assertions.assertEquals("testContent", result.getContent()),
                    () -> Assertions.assertEquals(currentUser, result.getUser())
            );
        }
    }

    @Nested
    @DisplayName("게시글 목록 조회 서비스 단위 테스트")
    class GetPosts {

        @DisplayName("성공")
        @Test
        void success_Test() {
            // Given
            Pageable pageable = PageRequest.of(0, 3);
            Page<Post> mockPage = Mockito.mock(Page.class);

            Mockito.when(postRepository.findAll(pageable)).thenReturn(mockPage);

            // When
            Page<Post> result = postService.getPosts(pageable);

            // Then
            Assertions.assertEquals(mockPage, result);
        }
    }

    @Nested
    @DisplayName("특정 게시글 조회 서비스 단위 테스트")
    class GetPostById {

        @DisplayName("성공")
        @Test
        void success_Test() {
            // Given
            Long postId = 1L;
            Post post = Post.builder().id(1L).title("title").content("content").build();

            Mockito.when(postRepository.findById(postId)).thenReturn(Optional.of(post));

            // When
            Post result = postService.getPostById(postId);

            // Then
            Assertions.assertEquals(post, result);
        }

        @DisplayName("실패 - 유효하지 않은 게시글 아이디")
        @Test
        void failure_Test_InvalidPostId() {
            // Given
            Long postId = 1L;
            Mockito.when(postRepository.findById(postId)).thenReturn(Optional.empty());

            // When & Then
            Assertions.assertThrows(Exception404.class, () -> postService.getPostById(postId));
        }
    }

    @Nested
    @DisplayName("게시글 수정 서비스 단위 테스트")
    class UpdatePost {

        @DisplayName("성공")
        @Test
        void success_Test() {
            // Given
            Long postId = 1L;

            User currentUser = User.builder().id(1L).build();

            PostRequest.PostDTO postDTO = PostRequest.PostDTO.builder()
                    .title("new title")
                    .content("new content")
                    .build();

            Post post = Post.builder().id(1L).title("title").content("content").user(currentUser).build();

            Mockito.when(postRepository.findById(postId)).thenReturn(Optional.of(post));

            // When
            Post result = postService.updatePost(postId, postDTO, currentUser);

            // Then
            Assertions.assertAll(
                    () -> Assertions.assertEquals(postDTO.getTitle(), result.getTitle()),
                    () -> Assertions.assertEquals(postDTO.getContent(), result.getContent()),
                    () -> Assertions.assertEquals(currentUser, result.getUser())
            );
        }

        @DisplayName("실패 - 유효하지 않은 게시글 아이디")
        @Test
        void failure_Test_InvalidPostId() {
            // Given
            Long postId = 1L;

            User currentUser = User.builder().id(1L).build();

            PostRequest.PostDTO postDTO = PostRequest.PostDTO.builder().build();

            Mockito.when(postRepository.findById(postId)).thenReturn(Optional.empty());

            // When & Then
            Assertions.assertThrows(Exception404.class, () -> postService.updatePost(postId, postDTO, currentUser));
        }

        @DisplayName("실패 - 유저 권한 없음")
        @Test
        void failure_Test_InvalidUser() {
            // Given
            Long postId = 1L;

            User currentUser = User.builder().id(2L).build();

            PostRequest.PostDTO postDTO = PostRequest.PostDTO.builder().build();

            User postUser = User.builder().id(1L).build();
            Post post = Post.builder().id(1L).title("title").content("content").user(postUser).build();

            Mockito.when(postRepository.findById(postId)).thenReturn(Optional.of(post));

            // When & Then
            Assertions.assertThrows(Exception403.class, () -> postService.updatePost(postId, postDTO, currentUser));
        }
    }

    @Nested
    @DisplayName("게시글 삭제 서비스 단위 테스트")
    class DeletePost {

        @DisplayName("성공")
        @Test
        void success_Test() {
            // Given
            Long postId = 1L;

            User currentUser = User.builder().id(1L).build();

            Post post = Post.builder().id(1L).title("title").content("content").user(currentUser).build();

            Mockito.when(postRepository.findById(postId)).thenReturn(Optional.of(post));

            // When
            postService.deletePost(postId, currentUser);

            // Then (모킹된 삭제 기능에 대한 검증)
            Mockito.verify(postRepository).delete(post);
        }

        @DisplayName("실패 - 유효하지 않은 게시글 아이디")
        @Test
        void failure_Test_InvalidPostId() {
            // Given
            Long postId = 1L;

            User currentUser = User.builder().id(1L).build();

            Mockito.when(postRepository.findById(postId)).thenReturn(Optional.empty());

            // When & Then
            Assertions.assertThrows(Exception404.class, () -> postService.deletePost(postId, currentUser));
        }

        @DisplayName("실패 - 유저 권한 없음")
        @Test
        void failure_Test_InvalidUser() {
            // Given
            Long postId = 1L;

            User currentUser = User.builder().id(2L).build();

            User postUser = User.builder().id(1L).build();
            Post post = Post.builder().id(1L).title("title").content("content").user(postUser).build();

            Mockito.when(postRepository.findById(postId)).thenReturn(Optional.of(post));

            // When & Then
            Assertions.assertThrows(Exception403.class, () -> postService.deletePost(postId, currentUser));

        }
    }
}