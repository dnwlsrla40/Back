package com.campuspick.demo.service;

import com.campuspick.demo.domain.entity.Post;
import com.campuspick.demo.domain.entity.Series;
import com.campuspick.demo.domain.repository.PostRepository;
import com.campuspick.demo.domain.repository.SeriesRepository;
import com.campuspick.demo.domain.repository.UserRepository;
import com.campuspick.demo.dto.SeriesResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SeriesService {

    private final SeriesRepository seriesRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<Series> getUserSeries(String username){
        return seriesRepository.findAllByUserUsername(username);
    }

    @Transactional(readOnly = true)
    public SeriesResponseDto getSeriesPosts(String username, String seriesUrl){
        Series series = seriesRepository.findByUserUsernameAndSeriesUrl(username, seriesUrl);
        List<Post> posts = postRepository.findAllByUserUsernameAndSeriesSeriesUrlOrderBySeriesIndex(username, seriesUrl);
        return new SeriesResponseDto(series, posts);
    }

}
