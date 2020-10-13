package com.campuspick.demo.controller;

import com.campuspick.demo.domain.entity.Post;
import com.campuspick.demo.dto.PostDto;
import com.campuspick.demo.util.UploadThumbnailUtils;
import lombok.RequiredArgsConstructor;
import com.campuspick.demo.service.PostService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    @GetMapping("/download")
    public ResponseEntity<Resource> fileDownload() throws IOException {
        Path path = Paths.get("C:/Users/PC/Desktop/resource/2020/10/13/s_8b183090-af00-46a7-a58e-841b892f4d91_test.PNG");
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "test" + "\"")
                .body(resource);
    }
}
