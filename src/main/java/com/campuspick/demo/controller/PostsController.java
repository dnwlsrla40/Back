package com.campuspick.demo.controller;

import com.campuspick.demo.domain.Posts;
import com.campuspick.demo.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostsController {

    private final PostsService postsService;

    @PostMapping("/write")
    public ObjectId save(@RequestBody Posts posts) {
        return postsService.insertPosts(posts).getId();
    }
}
