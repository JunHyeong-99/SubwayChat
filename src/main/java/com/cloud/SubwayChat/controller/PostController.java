package com.cloud.SubwayChat.controller;

import com.cloud.SubwayChat.domain.Post;
import com.cloud.SubwayChat.domain.PostType;
import com.cloud.SubwayChat.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    // 게시글 작성 폼으로 이동하기 위해 사용
    @GetMapping("/posts/new")
    public String createPostForm(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("types", PostType.values());

        return "createPost";
    }

    // 게시글 목록 조회
    @GetMapping("/posts")
    public String findPostList(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Post> pagePosts = postService.findPostList(page);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pagePosts.getTotalPages());
        model.addAttribute("totalItems", pagePosts.getTotalElements());
        model.addAttribute("posts", pagePosts.getContent());

        return "postsList";
    }

    @GetMapping("/posts/{id}")
    public String findPostById(Model model, @PathVariable Long id) {
        Post post = postService.findPostById(id);
        model.addAttribute("post", post);

        return "postDetail";
    }
}
