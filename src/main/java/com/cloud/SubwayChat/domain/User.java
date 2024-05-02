package com.cloud.SubwayChat.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_tb")
@NoArgsConstructor
@Getter
public class User extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String nickName;

    @Column
    private SubwayLine line;

    public User(String nickName, SubwayLine line) {
        this.nickName = nickName;
        this.line = line;
    }
}
