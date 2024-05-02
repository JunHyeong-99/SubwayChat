package com.cloud.SubwayChat.controller;

import com.cloud.SubwayChat.domain.SubwayLine;
import com.cloud.SubwayChat.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("lines", SubwayLine.values());
        return "createUser";
    }
}
