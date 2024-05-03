package com.cloud.SubwayChat.service;

import com.cloud.SubwayChat.domain.Post;
import com.cloud.SubwayChat.domain.PostType;
import com.cloud.SubwayChat.domain.User;
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
    private final EntityManager entityManager;

    @Transactional
    public void createPost(String title, String content, PostType type, Long userId){
        // 프록시 객체 사용
        User user = entityManager.getReference(User.class, userId);

        Post post = Post.builder()
                .user(user)
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
}
