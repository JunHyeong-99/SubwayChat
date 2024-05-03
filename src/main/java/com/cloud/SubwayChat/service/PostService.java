package com.cloud.SubwayChat.service;

import com.cloud.SubwayChat.domain.Post;
import com.cloud.SubwayChat.domain.PostType;
import com.cloud.SubwayChat.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public void createPost(String title, String content, PostType type){
        Post post = Post.builder()
                .title(title)
                .content(content)
                .type(type)
                .build();

        postRepository.save(post);
    }
}
