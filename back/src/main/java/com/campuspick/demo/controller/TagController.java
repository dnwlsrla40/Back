package com.campuspick.demo.controller;

import com.campuspick.demo.dto.TagDetailResponseDto;
import com.campuspick.demo.dto.TagListResponseDto;
import com.campuspick.demo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public List<TagListResponseDto> listTags() {
        return tagService.getTagList();
    }

    @GetMapping("/tags/{tagName}")
    public TagDetailResponseDto getTagDetail(@PathVariable String tagName) {
        return tagService.getTagDetail(tagName);
    }

}
