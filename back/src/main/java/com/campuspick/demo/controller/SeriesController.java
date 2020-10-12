package com.campuspick.demo.controller;

import com.campuspick.demo.domain.entity.Series;
import com.campuspick.demo.dto.SeriesCreateRequestDto;
import com.campuspick.demo.dto.SeriesResponseDto;
import com.campuspick.demo.service.SeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/velog.io")
@RequiredArgsConstructor
@RestController
public class SeriesController {

    private final SeriesService seriesService;

    @GetMapping("/@{username}/series")
    public List<Series> getUserSeries(@PathVariable String username){
        return seriesService.getUserSeries(username);
    }

    @GetMapping("/@{username}/series/{seriesUrl}")
    public SeriesResponseDto getSeriesPosts(@PathVariable String username, @PathVariable String seriesUrl){
        return seriesService.getSeriesPosts(username, seriesUrl);
    }

    // series 리스트 보여주기(시리즈에 추가하기 버튼 눌렀을 시)
    @GetMapping("/series")
    public List<Series> getSeries(){
        return seriesService.getSeriesList();
    }

    // series 추가
    @PostMapping("/series")
    public Series addSeries(@RequestBody SeriesCreateRequestDto seriesCreateRequestDto){
        return seriesService.addSeries(seriesCreateRequestDto);
    }

//    @PutMapping("/@{username}/series/{seriesName}")
//    public Series updateSeries(@PathVariable String username, @PathVariable String seriesName){
//
//    }

}
