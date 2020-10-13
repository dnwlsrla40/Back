package com.campuspick.demo.dto;

import lombok.Getter;

public class UserDto {

    @Getter
    public static class UserCreateRequestDto {
        private String email;
        private String username;
        private String shortBio;
    }
}
