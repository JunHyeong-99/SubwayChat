package com.cloud.SubwayChat.controller.dto;

import com.cloud.SubwayChat.domain.ChatRoom;
import com.cloud.SubwayChat.domain.SubwayLine;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Json으로 직렬화할 때 null 값은 포함하지 않도록
public class ChatRoomDto implements Serializable {       // Redis 에 저장되는 객체들이 직렬화가 가능하도록

    private static final long serialVersionUID = 6494678977089006639L;      // 역직렬화 위한 serialVersionUID 세팅
    private Long id;
    private String roomId;
    private SubwayLine subwayLine;


    public static ChatRoomDto create(SubwayLine subwayLine) {
        return ChatRoomDto.builder()
                .roomId(UUID.randomUUID().toString())
                .subwayLine(subwayLine)
                .build();
    }
    public ChatRoom toEntity() {
        return ChatRoom.builder()
                .roomId(this.roomId)
                .subwayLine(this.subwayLine)
                .build();
    }
}
