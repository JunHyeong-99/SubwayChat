package com.cloud.SubwayChat.service;

import com.cloud.SubwayChat.domain.Post;
import com.cloud.SubwayChat.domain.PostType;
import com.cloud.SubwayChat.domain.User;
import com.cloud.SubwayChat.repository.PostRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
