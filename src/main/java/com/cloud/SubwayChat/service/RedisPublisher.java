package com.cloud.SubwayChat.service;

import com.cloud.SubwayChat.controller.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisPublisher {
    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic topic, MessageDto messageDto) {
        redisTemplate.convertAndSend(topic.getTopic(), messageDto);
    }
}
