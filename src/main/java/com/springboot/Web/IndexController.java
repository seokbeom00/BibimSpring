package com.springboot.Web;

import com.springboot.Service.posts.PostsService;
import com.springboot.Web.dto.PostsResponseDto;
import com.springboot.security.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;
    private final HttpSession httpSession;
    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("posts", postsService.findAllDesc());
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        if(sessionUser != null){
            model.addAttribute("userName", sessionUser.getName());
        }
        return "index";
    }
    @GetMapping("/posts/save")
    public String postsSave(Model model) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        model.addAttribute("userName", sessionUser.getName());
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }

    @DeleteMapping("/api/v1/posts/{id}")
    @ResponseBody
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }
}
