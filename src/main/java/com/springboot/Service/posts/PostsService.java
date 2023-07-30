package com.springboot.Service.posts;

import com.springboot.Domain.member.Member;
import com.springboot.Domain.member.MemberRepository;
import com.springboot.Domain.posts.Posts;
import com.springboot.Domain.posts.PostsRepository;
import com.springboot.Web.dto.PostsListResponseDto;
import com.springboot.Web.dto.PostsResponseDto;
import com.springboot.Web.dto.PostsSaveRequestDto;
import com.springboot.Web.dto.PostsUpdateRequestDto;
import com.springboot.security.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service //얘를 서비스로 쓰겠다
public class PostsService {

    private final PostsRepository postsRepository;
    private final HttpSession httpSession;
    private final MemberRepository memberRepository;

    @Transactional
    public long save(PostsSaveRequestDto requestDto){
        Posts posts = requestDto.toEntity();
        SessionUser sessionUser = (SessionUser)httpSession.getAttribute("user");
        Member member = memberRepository.findByEmail(sessionUser.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일을 가진 사용자가 없습니다." +
                        "id: "+ sessionUser.getEmail()));
        posts.setMember(member);
        member.addPosts(posts);
        return postsRepository.save(posts).getId();
    }
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 게시글이 없습니다. id="+ id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete (Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));
        postsRepository.delete(posts);
    }
}
