package com.campuspick.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
import com.campuspick.demo.dto.PostResponseDto;
import com.campuspick.demo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/velog.io")
@RestController
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/@{username}/{postUrl}")
    public PostResponseDto getPost(@PathVariable String username, @PathVariable String postUrl){
        return postService.getPost(username, postUrl);
    }

    @GetMapping("/write")
    public String write() throws Exception {
        return "write";
    }
}
