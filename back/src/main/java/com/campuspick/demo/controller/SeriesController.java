package com.campuspick.demo.controller;

import com.campuspick.demo.domain.entity.Series;
import com.campuspick.demo.dto.SeriesResponseDto;
import com.campuspick.demo.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/velog.io")
@RestController
public class SeriesController {

    @Autowired
    SeriesService seriesService;

    @GetMapping("/@{username}/series")
    public List<Series> getUserSeries(@PathVariable String username){
        return seriesService.getUserSeries(username);
    }

    @GetMapping("/@{username}/series/{seriesUrl}")
    public SeriesResponseDto getSeriesPosts(@PathVariable String username, @PathVariable String seriesUrl){
        return seriesService.getSeriesPosts(username, seriesUrl);
    }

//    @PutMapping("/@{username}/series/{seriesName}")
//    public Series updateSeries(@PathVariable String username, @PathVariable String seriesName){
//
//    }

}
