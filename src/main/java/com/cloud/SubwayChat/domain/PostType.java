package com.cloud.SubwayChat.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PostType {

    CASUAL("잡담"),
    INFO("정보공유");

    private String value;
}
