package com.campuspick.demo.dto;

import lombok.Data;

@Data
public class JwtResponseDto {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String accesstoken;
    private final String refreshtoken;

    public JwtResponseDto(String accesstoken, String refreshtoken) {
        this.accesstoken = accesstoken;
        this.refreshtoken = refreshtoken;
    }
}
