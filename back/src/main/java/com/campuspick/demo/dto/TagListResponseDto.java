package com.campuspick.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TagListResponseDto {

    private String tagName;
    private int postCnt;

    public TagListResponseDto(String tagName, int postCnt) {
        this.tagName = tagName;
        this.postCnt = postCnt;
    }

}
