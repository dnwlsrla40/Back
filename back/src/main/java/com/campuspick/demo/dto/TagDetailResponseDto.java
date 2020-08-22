package com.campuspick.demo.dto;

import com.campuspick.demo.domain.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class TagDetailResponseDto {

    private List<Post> post;    // 태그를 가진 모든 포스트
    private int postCnt;        // 태그를 가진 포스트의 개수

    public TagDetailResponseDto(List<Post> post, int postCnt) {
        this.post = post;
        this.postCnt = postCnt;
    }

}
