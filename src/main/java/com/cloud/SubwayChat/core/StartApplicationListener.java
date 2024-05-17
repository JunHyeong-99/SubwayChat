package com.cloud.SubwayChat.core;

import com.cloud.SubwayChat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    private final ChatRoomService chatRoomService;
    // 스프링 부트가 로드될 때 채팅방 자동생성
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        chatRoomService.createRoom();
    }
}
