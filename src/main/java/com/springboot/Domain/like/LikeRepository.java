package com.springboot.Domain.like;

import com.springboot.Domain.member.Member;
import com.springboot.Domain.posts.Posts;
import com.springboot.Web.dto.MemberResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByMemberAndPosts(Member member, Posts posts);
    @Query("SELECT l.member FROM Likes l WHERE l.posts.id = :postsId ORDER BY l.id DESC")
    List<Member> findAllDesc(@Param("postsId") Long postsId);
}
