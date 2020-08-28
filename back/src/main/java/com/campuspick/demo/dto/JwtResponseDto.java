package com.campuspick.demo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtResponseDto implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String access_token;
    private final String refresh_token;

    public JwtResponseDto(String accesstoken, String refreshtoken) {
        this.access_token = accesstoken;
        this.refresh_token = refreshtoken;
    }
}
