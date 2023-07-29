package com.springboot.Web.dto;

import com.springboot.Domain.like.Likes;
import com.springboot.Domain.member.Member;
import com.springboot.Domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LikesSaveRequestDto {
    private Member member;
    private Posts posts;

    @Builder
    public LikesSaveRequestDto(Member member, Posts posts) {
        this.member = member;
        this.posts = posts;
    }

    public Likes toEntity() {
        return Likes.builder()
                .member(member)
                .posts(posts)
                .build();
    }
}
