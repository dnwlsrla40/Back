package com.campuspick.demo.util;

import lombok.Builder;
import lombok.Data;

@Data
public class TagHistoryUtil {
    private String name;

    public TagHistoryUtil(){

    }

    @Builder
    public TagHistoryUtil(String name) {
        this.name = name;
    }
}
