package com.cloud.SubwayChat.controller;

import com.cloud.SubwayChat.controller.dto.PostDto;
import com.cloud.SubwayChat.controller.dto.PostsDto;
import com.cloud.SubwayChat.core.errors.CustomException;
import com.cloud.SubwayChat.core.errors.ExceptionCode;
import com.cloud.SubwayChat.domain.Post;
import com.cloud.SubwayChat.domain.PostType;
import com.cloud.SubwayChat.service.PostService;
import jakarta.servlet.http.HttpSession;
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
    public String createPost(@ModelAttribute Post post, HttpSession session){
        // 세션에 USER_ID가 없다면 로그인 페이지로 리다이렉션
        Long userId = (Long) session.getAttribute("USER_ID");

        postService.createPost(post.getTitle(), post.getContent(), post.getType(), userId);

        return "redirect:/home";
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
    @ResponseBody
    public PostsDto findPostList(Model model, @RequestParam(defaultValue = "0") int page) {

        return postService.findPostList(page);
    }

    @GetMapping("/posts/{id}")
    public String findPostById(Model model, @PathVariable Long id) {
        PostDto.Detail postDetail = postService.findPostDetailById(id);

        model.addAttribute("post", postDetail);
        return "postDetail";
    }

    @GetMapping("/posts/{id}/update")
    public String updatePostForm(@PathVariable Long id, HttpSession session, Model model) {

        PostDto.Detail postDetail = postService.findPostDetailById(id);
        model.addAttribute("post", postDetail);
        model.addAttribute("types", postDetail.getType());

        return "updatePost";
    }

    @PostMapping("/posts/{id}/update")
    public String updatePost(@PathVariable Long id, @ModelAttribute Post post, HttpSession session) {
        Long userId = (Long) session.getAttribute("USER_ID");

        postService.updatePost(id, userId, post.getTitle(), post.getContent(), post.getType());

        return "redirect:/home";
    }

    @PostMapping("/posts/{postId}/delete")
    public String deletePost(@PathVariable Long postId, HttpSession session){
        Long userId = (Long) session.getAttribute("USER_ID");

        postService.deletePost(postId, userId);

        return "redirect:/posts";
    }

    @PostMapping("/posts/{postId}/comments")
    public String createComment(@RequestParam("content") String content, @PathVariable Long postId, HttpSession session) {
        Long userId = (Long) session.getAttribute("USER_ID");
        postService.createComment(content, userId, postId);
        return "redirect:/posts/" + postId;
    }

    @PostMapping("/posts/{postId}/comments/{id}/update")
    public String updateComment(@PathVariable Long id, @RequestParam("content") String content, @PathVariable Long postId, HttpSession session) {
        Long userId = (Long) session.getAttribute("USER_ID");

        postService.updateComment(content, userId, id);
        return "redirect:/posts/" + postId;
    }
}
