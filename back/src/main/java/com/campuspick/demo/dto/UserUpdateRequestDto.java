package com.campuspick.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {

    private String velogName;
    private String shortBio;
    private Blob thumbnail;
    private Blob profileLinks;

    @Builder
    public UserUpdateRequestDto(Blob thumbnail, String shortBio, String velogName, Blob profileLinks){
        this.velogName = velogName;
        this.shortBio = shortBio;
        this.thumbnail = thumbnail;
        this.profileLinks = profileLinks;
    }
}
