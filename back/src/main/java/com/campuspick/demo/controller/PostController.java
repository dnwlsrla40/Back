package com.campuspick.demo.controller;

import com.campuspick.demo.domain.entity.Post;
import com.campuspick.demo.dto.PostDto;
import com.campuspick.demo.util.UploadThumbnailUtils;
import lombok.RequiredArgsConstructor;
import com.campuspick.demo.service.PostService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/velog.io")
@RestController
public class PostController {

    private final PostService postService;

    @Value("${resources.location}")
    private String thumbnailPath;

    // write 페이지 이동
    @GetMapping("/write")
    public String write(){
        return "write";
    }

    // post 등록(출간하기 버튼 눌렀을 시)
    @PostMapping("/write")
    public Post write(@RequestBody PostDto.PostCreateRequestDto post){
        return postService.write(post);
    }

    // thumbnail 업로드
    @PostMapping("/thumbnail")
    public String uploadThumbnail(@RequestPart("file") MultipartFile file) throws Exception{
        return UploadThumbnailUtils.fileUpload(thumbnailPath, file.getOriginalFilename(), file.getBytes());
    }
}
