package com.campuspick.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SeriesCreateRequestDto {
    private String name;
    // url도 추가 해줘야 함 (어떤 방식으로 할지 정해지면)
    private String url;
}
