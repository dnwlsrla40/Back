package com.campuspick.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class CommentRequestDto {

    private UUID postId;
    private UUID userId;
    private UUID replyTo;
    private String text;

    @Builder
    public CommentRequestDto(String postId, String userId, String replyTo, String text){
        this.postId = UUID.fromString(postId);
        this.userId = UUID.fromString(userId);
        this.text = text;

        if(replyTo!=null) this.replyTo = UUID.fromString(replyTo);
        else this.replyTo = null;
    }

}
