package com.campuspick.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/velog.io")
@RestController
public class PostController {

    @GetMapping("/write")
    public String write() throws Exception {
        return "write";
    }
}
