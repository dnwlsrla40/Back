package com.campuspick.demo.controller;

import com.campuspick.demo.domain.entity.Post;
import com.campuspick.demo.dto.PostCreateRequestDto;
import com.campuspick.demo.util.UploadThumbnailUtils;
import lombok.RequiredArgsConstructor;
import com.campuspick.demo.dto.PostResponseDto;
import com.campuspick.demo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Post write(@RequestBody PostCreateRequestDto post){
        return postService.write(post);
    }

    // thumbnail 업로드
    @PostMapping("/thumbnail")
    public String uploadThumbnail(@RequestPart("file") MultipartFile file) throws Exception{
//        File f = new File("C:\\Users\\PC\\Desktop\\resource\\2020\\09\\29\\4fb75152-a985-4b1b-a201-624809f185b6_test 이미지.PNG");
//        System.out.println(f.getName());
//        System.out.println(f.getPath());
//        System.out.println(f.length());
        return UploadThumbnailUtils.fileUpload(thumbnailPath, file.getOriginalFilename(), file.getBytes());
    }
}
