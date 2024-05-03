package com.cloud.SubwayChat.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "message")
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender")
    private String sender;

    @Column(name = "roomId")
    private String roomId;

    @Column(name = "receiver")
    private String receiver;

    @Column(name = "message")
    private String message;

    @Column(name = "sentTime")
    private String sentTime;

    // 1.
    @ManyToOne
    @JoinColumn(name = "roomId", referencedColumnName = "roomId", insertable = false, updatable = false)
    private MessageRoom messageRoom;

    // 대화 저장
    public Message(String sender, String roomId, String message) {
        super();
        this.sender = sender;
        this.roomId = roomId;
        this.message = message;
    }
}