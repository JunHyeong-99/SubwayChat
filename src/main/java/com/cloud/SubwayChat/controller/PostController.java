package com.cloud.SubwayChat.controller;

import com.cloud.SubwayChat.domain.Post;
import com.cloud.SubwayChat.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public String createPost(@ModelAttribute Post post, @SessionAttribute("USER_ID") Long userId){
        // 세션에 USER_ID가 없다면 로그인 페이지로 리다이렉션
        if (userId == null) {
            return "redirect:/login";
        }

        postService.createPost(post.getTitle(), post.getContent(), post.getType(), userId);

        return "redirect:/posts";
    }
}
