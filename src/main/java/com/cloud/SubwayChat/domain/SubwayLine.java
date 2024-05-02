package com.cloud.SubwayChat.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SubwayLine {
    LINE_1("1호선"),
    LINE_2("2호선"),
    LINE_3("3호선"),
    LINE_4("4호선"),
    DONGHAE("동해선"),
    GIMHAE_LIGHT_RAIL("부산김해경전철");

    private String lineName;
}
