package com.campuspick.demo.dto;

import com.campuspick.demo.util.TagHistoryUtil;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

import java.util.List;

public class PostHistoryDto {

    @Getter
    public static class PostHistoryCreateRequestDto{
        private String title;
        private String body;

        private List<TagHistoryUtil> tagList;

        @Builder
        public PostHistoryCreateRequestDto(String title, String body, List<TagHistoryUtil> tagList){

            Assert.notNull(title, "title must not be null");
            Assert.notNull(body, "body must not be null");

            this.title = title;
            this.body = body;
            this.tagList = tagList;
        }
    }

    @Getter
    public static class PostHistoryCreateResponseDto{
        private String title;
        private String body;
        private String url;
        private List<TagHistoryUtil> tagList;

        @Builder
        public PostHistoryCreateResponseDto(String title, String body, String url, List<TagHistoryUtil> tagList){

            Assert.notNull(title, "title must not be null");
            Assert.notNull(body, "body must not be null");

            this.title = title;
            this.body = body;
            this.url = url;
            this.tagList = tagList;
        }
    }
}
