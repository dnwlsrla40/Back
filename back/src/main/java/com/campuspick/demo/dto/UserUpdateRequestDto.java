package com.campuspick.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {

    private String displayName;
    private String shortBio;
    private Blob thumbnail;
    private Blob profileLinks;

    @Builder
    public UserUpdateRequestDto(String displayName, String shortBio, Blob thumbnail, Blob profileLinks){
        this.displayName = displayName;
        this.shortBio = shortBio;
        this.thumbnail = thumbnail;
        this.profileLinks = profileLinks;
    }
}
