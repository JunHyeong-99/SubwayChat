package com.cloud.SubwayChat.service;

import com.cloud.SubwayChat.controller.dto.MessageDto;
import com.cloud.SubwayChat.domain.Message;
import com.cloud.SubwayChat.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    private final RedisTemplate<String, MessageDto> redisTemplateMessage;
    private final MessageRepository messageRepository;

    // 메시지 저장
    public void saveMessage(MessageDto messageDto) {
        // DB에 메시지 저장
        Message message = new Message(messageDto.getSender(), messageDto.getRoomId(), messageDto.getMessage());
        messageRepository.save(message);
        // 메시지 직렬화 하기 redis에 저장하기 위해 직렬화
        redisTemplateMessage.setValueSerializer(new Jackson2JsonRedisSerializer<>(MessageDto.class));
        // 직렬화한 메시지 redis에 저장 roomID에 해당하는 List에 메시지 내용 저장
        redisTemplateMessage.opsForList().rightPush(messageDto.getRoomId(), messageDto);
        // 메시지 만료 기간 15분으로 설정
        redisTemplateMessage.expire(messageDto.getRoomId(), 15, TimeUnit.MINUTES);
    }

}
