package com.cloud.SubwayChat.controller;

import com.cloud.SubwayChat.controller.dto.MessageDto;
import com.cloud.SubwayChat.service.ChatRoomService;
import com.cloud.SubwayChat.service.ChatService;
import com.cloud.SubwayChat.service.RedisPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final RedisPublisher redisPublisher;
    private final ChatRoomService chatRoomService;
    private final ChatService chatService;

    // 대화 & 대화 저장
    @MessageMapping("/message")
    public void message(MessageDto messageDto) {
        // 클라이언트의 topic 입장, 채팅을 위해 리스너와 연동
        chatRoomService.enterChatRoom(messageDto.getRoomId());

        // Websocket 에 발행된 메시지를 redis 로 발행. 해당 채팅방을 구독한 클라이언트에게 메시지가 실시간 전송된다
        redisPublisher.publish(chatRoomService.getTopic(messageDto.getRoomId()), messageDto);

        // Mysql, Redis 에 채팅 저장
        chatService.saveMessage(messageDto);
    }
    // 채팅 내역 조회
    @GetMapping("/api/room/{roomId}/message")
    public ResponseEntity<List<MessageDto>> loadMessage(@PathVariable String roomId) {
        return ResponseEntity.ok(chatRoomService.loadMessage(roomId));
    }
}
