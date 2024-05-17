package com.cloud.SubwayChat.controller;

import com.cloud.SubwayChat.controller.dto.ChatRoomDto;
import com.cloud.SubwayChat.domain.SubwayLine;
import com.cloud.SubwayChat.domain.User;
import com.cloud.SubwayChat.service.ChatRoomService;
import com.cloud.SubwayChat.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ChatRoomService chatRoomService;

    @GetMapping("/")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());

        return "createUser";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute User user, HttpSession session) {
        userService.join(user.getNickName(), session);

        return "mainPage";
    }
}
