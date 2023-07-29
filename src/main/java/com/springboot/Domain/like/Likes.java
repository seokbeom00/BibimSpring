package com.springboot.Domain.like;

import com.springboot.Domain.BaseTimeEntity;
import com.springboot.Domain.member.Member;
import com.springboot.Domain.posts.Posts;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Likes extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "POSTS_ID")
    private Posts posts;

    @Builder
    public Likes(Member member, Posts posts){
        this.member = member;
        this.posts = posts;
    }
}
