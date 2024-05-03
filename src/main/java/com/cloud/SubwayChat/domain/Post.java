package com.cloud.SubwayChat.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "post_tb")
@NoArgsConstructor
@Getter @Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private PostType type;

    public Post(String title, String content, PostType type) {
        this.title = title;
        this.content = content;
        this.type = type;
    }
}
