package com.cloud.SubwayChat.controller.dto;

import com.cloud.SubwayChat.domain.Post;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class PostsDto {
    private int currentPage;
    private int totalPages;
    private Long totalItems;
    private List<PostDto> posts;

    public static PostsDto toDto(Page<Post> postsPage, int pageNo){
        return PostsDto.builder()
                .currentPage(pageNo)
                .totalPages(postsPage.getTotalPages())
                .totalItems(postsPage.getTotalElements())
                .posts(postsPage.getContent().stream()
                        .map(PostDto::toDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
