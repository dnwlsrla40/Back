package com.campuspick.demo.dto;

import com.campuspick.demo.domain.entity.Tag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TagDto {

    private String name;

    @Builder
    public TagDto(String name) {
        this.name = name;
    }

    public Tag TagDtoToEntity(){
        Tag tag = Tag.builder()
                .name(this.name)
                .build();
        return tag;
    }

}
