package com.cloud.SubwayChat.controller;

import com.cloud.SubwayChat.controller.dto.ChatRoomDto;
import com.cloud.SubwayChat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoomDto> room() {
        return chatRoomService.getAllChatRoom();
    }

    @GetMapping("/room/enter/{roomId}")
    public String roomEnter(@PathVariable String roomId) {
        return "/chatDetail";
    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoomDto roomInfo(@PathVariable String roomId) {
        return chatRoomService.getChatRoom(roomId);
    }


}
