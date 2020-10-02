package com.campuspick.demo.dto;

import com.campuspick.demo.domain.entity.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostResponseDto {

    private Post post;
    private List<Comment> comments;
    private List<Tag> tags;

    public PostResponseDto(Post post, List<Comment> comments, List<Tag> tags){
        this.post = post;
        this.comments = comments;
        this.tags = tags;
    }

}
