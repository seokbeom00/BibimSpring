package com.springboot.Web.dto;

import com.springboot.Domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostsUpdateRequestDto {
    private String title;
    private String content;

    @Builder
    public PostsUpdateRequestDto(Posts entity) {
        this.title = entity.getTitle();
        this.content = entity.getContent();
    }
}
