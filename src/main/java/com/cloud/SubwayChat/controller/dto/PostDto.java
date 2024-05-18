package com.cloud.SubwayChat.controller.dto;

import com.cloud.SubwayChat.domain.Comment;
import com.cloud.SubwayChat.domain.Post;
import com.cloud.SubwayChat.domain.PostType;
import com.cloud.SubwayChat.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class PostDto {
    private Long postId;
    private String title;

    public static PostDto toDto(Post post) {
        return PostDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .build();
    }

    @Getter
    @Builder
    public static class Detail {
        private Long postId;
        private String title;
        private String content;
        private String nickName;
        private PostType type;
        private List<CommentDto> comments;

        public static PostDto.Detail toDto(Post post) {
            return Detail.builder()
                    .postId(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .nickName(post.getUser().getNickName())
                    .type(post.getType())
                    .comments(post.getComments().stream().map(CommentDto::toDto).collect(Collectors.toList()))
                    .build();
        }


    }
}
