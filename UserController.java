package com.cloud.SubwayChat.controller;

import com.cloud.SubwayChat.controller.dto.ChatRoomDto;
import com.cloud.SubwayChat.controller.dto.PostsDto;
import com.cloud.SubwayChat.controller.dto.UserDto;
import com.cloud.SubwayChat.domain.Post;
import com.cloud.SubwayChat.domain.SubwayLine;
import com.cloud.SubwayChat.domain.User;
import com.cloud.SubwayChat.service.ChatRoomService;
import com.cloud.SubwayChat.service.PostService;
import com.cloud.SubwayChat.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PostService postService;

    @GetMapping("/")
    public String createUserForm(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("USER_ID");
        if (userId != null) {
            return "redirect:/home";
        }

        model.addAttribute("user", new User());

        return "createUser";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute User user, HttpSession session) {
        userService.join(user.getNickName(), session);
        return "redirect:/home";
    }
    @GetMapping("/home")
    public String home(Model model, HttpSession session, @RequestParam(defaultValue = "0") int page) {
        UserDto userDto = userService.findUser((Long) session.getAttribute("USER_ID"));
        model.addAttribute("nickName", userDto.getNickName());
        PostsDto pagePosts = postService.findPostList(page);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pagePosts.getTotalPages());
        model.addAttribute("totalItems", pagePosts.getTotalItems());
        model.addAttribute("posts", pagePosts.getPosts());
        return "mainPage";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/"; 
    }
}
