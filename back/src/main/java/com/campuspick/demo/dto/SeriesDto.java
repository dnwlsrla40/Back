package com.campuspick.demo.dto;

import com.campuspick.demo.domain.entity.Post;
import com.campuspick.demo.domain.entity.Series;
import com.campuspick.demo.domain.entity.User;
import lombok.*;
import org.springframework.util.Assert;

import java.util.List;

public class SeriesDto {

    @Getter
    @NoArgsConstructor
    public static class SeriesCreateRequestDto {
        private String name;
        // url도 추가 해줘야 함 (어떤 방식으로 할지 정해지면)
        private String url;
    }

    @RequiredArgsConstructor
    @Getter
    public static class SeriesResponseDto {

        private Series series;
        private List<Post> posts;

        public SeriesResponseDto(Series series, List<Post> posts){
            this.series = series;
            this.posts = posts;
        }
    }


    @Getter
    public static class PostCreateSeriesDto {
        private String name;

        public PostCreateSeriesDto(String name){
            this.name = name;
        }
    }
}
