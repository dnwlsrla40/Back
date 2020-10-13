package com.campuspick.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public class PostDto {

    @NoArgsConstructor
    @Data
    public static class PostCreateRequestDto {
        private String title;
        private String body;
        private String url;
        private String shortDescription;
        private String thumbnail;
        private Boolean isPrivate;

        private SeriesDto.PostCreateSeriesDto series;
        private List<TagDto> tagList;

        public PostCreateRequestDto(String title, String body, String url, String shortDescription, String thumbnail, Boolean isPrivate, SeriesDto.PostCreateSeriesDto series, List<TagDto> tagList){

            Assert.notNull(title, "title must not be null");
            Assert.notNull(body, "body must not be null");
            Assert.notNull(url, "url must not be null");
            Assert.notNull(isPrivate, "isPrivate must not be null");

            this.title = title;
            this.body = body;
            this.url = url;
            this.shortDescription = shortDescription;
            this.thumbnail = thumbnail;
            this.isPrivate = isPrivate;
            this.series = series;
            this.tagList = new ArrayList<>();
            for(TagDto tagDto : tagList){
                this.tagList.add(new TagDto(tagDto.getName()));
            }
        }
    }
}
