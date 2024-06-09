package com.cloud.SubwayChat.controller.dto;

import com.cloud.SubwayChat.domain.Comment;
import com.cloud.SubwayChat.domain.Post;
import com.cloud.SubwayChat.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentDto {
    private Long id;
    private String nickName;
    private String content;

    public static CommentDto toDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .nickName(comment.getUser().getNickName())
                .content(comment.getContent())
                .build();
    }
}
