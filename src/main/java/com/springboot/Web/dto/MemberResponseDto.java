package com.springboot.Web.dto;

import com.springboot.Domain.like.Likes;
import com.springboot.Domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberResponseDto {
    private Long id;
    private Member member;

    @Builder
    public MemberResponseDto(Likes likes){
        this.id = likes.getId();
        this.member = likes.getMember();
    }

    @Builder
    public MemberResponseDto(Member member){
        this.id = member.getId();
        this.member = member;
    }
}
