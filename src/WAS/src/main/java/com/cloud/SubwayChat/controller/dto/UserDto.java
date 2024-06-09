package com.cloud.SubwayChat.controller.dto;

import lombok.Getter;

@Getter
public class UserDto {
    private Long userId;
    private String nickName;

    public UserDto(Long userId, String nickName){
        this.userId = userId;
        this.nickName = nickName;
    }
}
