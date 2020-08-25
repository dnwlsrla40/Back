package com.campuspick.demo.dto;

import com.campuspick.demo.domain.entity.PostTag;
import com.campuspick.demo.domain.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserVelogResponseDto {

    private User user;
    private List<PostTag> postTags;

    public UserVelogResponseDto(User user, List<PostTag> postTags){
        this.user = user;
        this.postTags = postTags;
    }

}
