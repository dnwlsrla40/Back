package com.campuspick.demo.controller;

import com.campuspick.demo.dto.TagDetailResponseDto;
import com.campuspick.demo.dto.TagListResponseDto;
import com.campuspick.demo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/velog.io")
@RestController
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public List<TagListResponseDto> listTags(@RequestParam(defaultValue = "trending") String sort, int page) {
        return tagService.getTagList(sort, page);
    }

    @GetMapping("/tags/{tagName}")
    public TagDetailResponseDto getTagDetail(@PathVariable String tagName) {
        return tagService.getTagDetail(tagName);
    }

}
