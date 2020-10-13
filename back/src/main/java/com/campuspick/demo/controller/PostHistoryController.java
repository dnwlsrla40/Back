package com.campuspick.demo.controller;

import com.campuspick.demo.domain.entity.PostHistory;
import com.campuspick.demo.dto.PostHistoryDto;
import com.campuspick.demo.service.PostHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/velog.io")
@RequiredArgsConstructor
@RestController
public class PostHistoryController {
    private final PostHistoryService postHistoryService;

    // 임시 게시글 목록 가져오기
    @GetMapping("/saves")
    public List<PostHistory> saves(){
        return postHistoryService.readPostHistoryHist();
    }

    // 임시 게시글 detail 가져오기
    @GetMapping("/writes")
    public PostHistory writes(@RequestParam UUID id) {
        return postHistoryService.write(id);
    }

    // 임시 게시글 저장
    @PostMapping("/saves")
    public PostHistoryDto.PostHistoryCreateResponseDto saves(@RequestBody PostHistoryDto.PostHistoryCreateRequestDto request){
        System.out.println("success");
        PostHistory postHistory = postHistoryService.saves(request);
        return postHistory.postHistoryToPostHistoryCreateResponseDto();
    }
}
