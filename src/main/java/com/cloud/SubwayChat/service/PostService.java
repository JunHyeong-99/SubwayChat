package com.cloud.SubwayChat.service;

import com.cloud.SubwayChat.core.errors.CustomException;
import com.cloud.SubwayChat.core.errors.ExceptionCode;
import com.cloud.SubwayChat.domain.Comment;
import com.cloud.SubwayChat.domain.Post;
import com.cloud.SubwayChat.domain.PostType;
import com.cloud.SubwayChat.domain.User;
import com.cloud.SubwayChat.repository.CommentRepository;
import com.cloud.SubwayChat.repository.PostRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final EntityManager entityManager;

    @Transactional
    public void createPost(String title, String content, PostType type, Long userId){
        // 프록시 객체 사용
        User userRef = entityManager.getReference(User.class, userId);

        Post post = Post.builder()
                .user(userRef)
                .title(title)
                .content(content)
                .type(type)
                .build();

        postRepository.save(post);
    }

    @Transactional
    public Page<Post> findPostList(int pageNo){
        Pageable pageable = PageRequest.of(pageNo, 10);

        return postRepository.findAll(pageable);
    }

    @Transactional
    public Post findPostById(Long postId){
        return postRepository.findByIdWithComment(postId).orElseThrow(
                () -> new CustomException(ExceptionCode.POST_NOT_FOUND)
        );
    }

    @Transactional
    public boolean updatePost(Long postId, Long userId, String title, String content, PostType type){
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ExceptionCode.POST_NOT_FOUND)
        );

        // 권한 없음
        if(!userId.equals(post.getUser().getId())){
            throw new CustomException(ExceptionCode.USER_FORBIDDEN);
        }

        post.updatePost(title, content, type);

        return false;
    }

    @Transactional
    public void createComment(String content, Long userId, Long postId){
        // 존재하지 않는 게시글이면 에러
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ExceptionCode.POST_NOT_FOUND)
        );

        // 프록시 객체 사용
        User userRef = entityManager.getReference(User.class, userId);

        Comment comment = Comment.builder()
                .user(userRef)
                .post(post)
                .content(content)
                .build();

        commentRepository.save(comment);

        // 양방향 메서드
        post.addComment(comment);
    }
}