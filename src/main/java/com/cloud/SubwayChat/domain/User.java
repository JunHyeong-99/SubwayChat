package com.cloud.SubwayChat.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_tb")
@NoArgsConstructor
@Getter @Setter
public class User extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String nickName;


    @Builder
    public User(String nickName) {
        this.nickName = nickName;
    }
}
