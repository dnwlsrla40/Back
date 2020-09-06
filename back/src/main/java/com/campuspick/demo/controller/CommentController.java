package com.campuspick.demo.controller;

import com.campuspick.demo.domain.entity.Comment;
import com.campuspick.demo.dto.CommentRequestDto;
import com.campuspick.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/velog.io")
@RestController
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/comment")
    public List<Comment> getComments(String postId){
        return commentService.getComments(UUID.fromString(postId));
    }

    @PostMapping("/comment")
    public Comment saveComment(@RequestBody CommentRequestDto requestDto){
        return commentService.saveComment(requestDto);
    }

}
