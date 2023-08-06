package com.springboot.Service.like;

import com.springboot.Domain.like.LikeRepository;
import com.springboot.Domain.like.Likes;
import com.springboot.Domain.member.Member;
import com.springboot.Domain.member.MemberRepository;
import com.springboot.Domain.posts.Posts;
import com.springboot.Domain.posts.PostsRepository;
import com.springboot.Web.dto.MemberResponseDto;
import com.springboot.Web.dto.LikesSaveRequestDto;
import com.springboot.exception.MemberNotExistException;
import com.springboot.exception.PostsNotExistException;
import com.springboot.security.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final PostsRepository postsRepository;
    private final HttpSession httpSession;

    @Transactional
    public long save(Long memberid, Long postsid){
        Member member = memberRepository.findById(memberid)
                .orElseThrow(() -> new MemberNotExistException("해당 id의 멤버가 없습니다. id: " + memberid));
        Posts posts = postsRepository.findById(postsid)
                .orElseThrow(() -> new PostsNotExistException("해당 id의 게시물이 없습니다. id: " + postsid));
        Optional<Likes> likes = likeRepository.findByMemberAndPosts(member, posts);
        if (likes.isPresent()) {
            throw new IllegalStateException("해당 멤버는 이미 이 게시물을 좋아했습니다.");
        }
        LikesSaveRequestDto requestDto = LikesSaveRequestDto.builder()
                .member(member)
                .posts(posts)
                .build();
        posts.addCount();
        return likeRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public long delete (Long postsId) {
        SessionUser user = (SessionUser)httpSession.getAttribute("user");
        Member member = memberRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일 회원이 없습니다."));
        Posts posts = postsRepository.findById(postsId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다. id=" + postsId));
        Likes likes = likeRepository.findByMemberAndPosts(member, posts)
                        .orElseThrow(() -> new IllegalArgumentException("해당 좋아요가 없습니다"));
        likeRepository.delete(likes);
        return likes.getId();
    }

    @Transactional
    public int getLikeCount(Long postsId) {
        Posts posts = postsRepository.findById(postsId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 게시물이 없습니다. id: " + postsId));
        return posts.getLikecount();
    }

    @Transactional
    public MemberResponseDto findMemberById(Long likeId) {
        Likes likes = likeRepository.findById(likeId).orElseThrow(()
                -> new IllegalArgumentException("해당 좋아요가 없습니다. id="+ likeId));
        return new MemberResponseDto(likes);
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDto> findAllMember(Long postsId) {
        return likeRepository.findAllDesc(postsId).stream()
                .map(MemberResponseDto::new)
                .collect(Collectors.toList());
    }
}
