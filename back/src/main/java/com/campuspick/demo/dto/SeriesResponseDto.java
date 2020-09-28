package com.campuspick.demo.dto;

import com.campuspick.demo.domain.entity.Post;
import com.campuspick.demo.domain.entity.Series;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class SeriesResponseDto {

    private Series series;
    private List<Post> posts;

    public SeriesResponseDto(Series series, List<Post> posts){
        this.series = series;
        this.posts = posts;
    }
}
