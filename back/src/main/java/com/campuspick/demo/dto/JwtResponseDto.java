package com.campuspick.demo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtResponseDto implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String accesstoken;
    private final String refreshtoken;

    public JwtResponseDto(String accesstoken, String refreshtoken) {
        this.accesstoken = accesstoken;
        this.refreshtoken = refreshtoken;
    }
}
