package com.cloud.SubwayChat.service;

import com.cloud.SubwayChat.controller.dto.ChatRoomDto;
import com.cloud.SubwayChat.controller.dto.MessageDto;
import com.cloud.SubwayChat.core.errors.CustomException;
import com.cloud.SubwayChat.domain.ChatRoom;
import com.cloud.SubwayChat.domain.Message;
import com.cloud.SubwayChat.domain.SubwayLine;
import com.cloud.SubwayChat.repository.ChatRoomRepository;
import com.cloud.SubwayChat.repository.MessageRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.cloud.SubwayChat.core.errors.ExceptionCode.CHAT_ROOM_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;
    private final RedisTemplate<String, MessageDto> redisTemplateMessage;

    // 채팅방 (topic)에 발행되는 메시지 처리하는 리스너
    private final RedisMessageListenerContainer redisMessageListener;

    // 구독 처리 서비스
    private final RedisSubscriber redisSubscriber;
    private static final String Chat_Rooms = "CHAT_ROOM";
    private final RedisTemplate<String, Object> redisTemplate;

    private HashOperations<String, String, ChatRoomDto> opsHashMessageRoom; //Hash 자료구조 Key,  새로운 엔트리의 키(Key), 엔트리 속 필드에 저장되는 value 타입

    // 2. 채팅방의 대화 메시지 발행을 위한 redis topic(쪽지방) 정보
    private Map<String, ChannelTopic> topics;

    // 3. redis 의 Hash 데이터 다루기 위함
    @PostConstruct
    private void init() {
        opsHashMessageRoom = redisTemplate.opsForHash();
        topics = new HashMap<>();
    }

    // chatRoom 생성 관리자가 미리 지하철 노선도에 따른 chatRoom을 생성해 둠
    public void createRoom() {
        for (SubwayLine subwayLine : SubwayLine.values()) {
            if (!chatRoomRepository.existsBySubwayLine(subwayLine)) {
                ChatRoomDto chatRoomDto = ChatRoomDto.create(subwayLine);
                ChatRoom chatRoom = chatRoomDto.toEntity();
                chatRoomRepository.save(chatRoom);
                // chatRoom 생성 redis CHAT_ROOM에 저장
                opsHashMessageRoom.put(Chat_Rooms, chatRoomDto.getRoomId(), chatRoomDto);
            }
        }
    }
    // 채팅방 입장 topic등록, 들어가는 것 이미 존재하는 chatRoom이어야 한다.
    public void enterChatRoom(String roomId) {
        ChannelTopic topic = topics.get(roomId);

        if (topic == null) {
            topic = new ChannelTopic(roomId);
            redisMessageListener.addMessageListener(redisSubscriber, topic); // pub/sub 통신을 위해 리스너를 설정
            topics.put(roomId, topic);
        }
    }

    public List<ChatRoomDto> getAllChatRoom() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();
        return chatRooms.stream().map(ChatRoomDto::toDto).toList();
    }
    public ChatRoomDto getChatRoom(String roomId) {
        return chatRoomRepository.findByRoomId(roomId)
                .map(ChatRoomDto::toDto)
                .orElseThrow(() -> new CustomException(CHAT_ROOM_NOT_FOUND));
    }

    // redis 채널에서 채팅방 조회
    public ChannelTopic getTopic(String roomId) {
        return topics.get(roomId);
    }

    public List<MessageDto> loadMessage(String roomId) {
        List<MessageDto> messageList = new ArrayList<>();

        // Redis 에서 해당 채팅방의 메시지 100개 불러오기
        List<MessageDto> redisMessageList = redisTemplateMessage.opsForList().range(roomId, 0, 99);

        // Redis 에서 가져온 메시지가 없다면, DB 에서 메시지 100개 가져오기
        if (redisMessageList == null || redisMessageList.isEmpty()) {
            List<Message> dbMessageList = messageRepository.findTop100ByRoomIdOrderBySentTimeAsc(roomId);

            for (Message message : dbMessageList) { // DB에서 가져온 메시지를 Redis에 저장
                MessageDto messageDto = new MessageDto(message);
                messageList.add(messageDto);
                redisTemplateMessage.setValueSerializer(new Jackson2JsonRedisSerializer<>(Message.class));
                redisTemplateMessage.opsForList().rightPush(roomId, messageDto);
            }
        } else {
            messageList.addAll(redisMessageList);
        }

        return messageList;
    }
}
