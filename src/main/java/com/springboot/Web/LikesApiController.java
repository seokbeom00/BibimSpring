package com.springboot.Web;

import com.springboot.Service.like.LikesService;
import com.springboot.Web.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class LikesApiController {
    private final LikesService likeService;

    @PostMapping("like/{memberId}/{postsId}")
    public long save(@PathVariable Long memberId, @PathVariable Long postsId){
        return likeService.save(memberId, postsId);
    }

    @DeleteMapping("like/{postsId}")
    public long delete(@PathVariable Long postsId){
        return likeService.delete(postsId);
    }

    @GetMapping("like/count/{postsId}")
    public int getLikeCount(@PathVariable Long postsId){
        return likeService.getLikeCount(postsId);
    }

    @GetMapping("like/{likeId}")
    public MemberResponseDto findMemberById(@PathVariable Long likeId){
        return likeService.findMemberById(likeId);
    }


    @GetMapping("like/findAll/{postsId}")
    public List<MemberResponseDto> findAllMember(@PathVariable Long postsId){
        return likeService.findAllMember(postsId);
    }
}
