package com.cloud.SubwayChat.controller.dto;

import com.cloud.SubwayChat.domain.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageDto {
    private String sender;
    private String roomId;
    private String message;
    private String sentTime;

    public MessageDto(Message message) {
        this.sender = message.getSender();
        this.roomId = message.getRoomId();
        this.message = message.getMessage();
    }
}
