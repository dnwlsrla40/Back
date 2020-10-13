package com.campuspick.demo.service;

import com.campuspick.demo.domain.entity.Post;
import com.campuspick.demo.domain.entity.Series;
import com.campuspick.demo.domain.entity.User;
import com.campuspick.demo.domain.repository.PostRepository;
import com.campuspick.demo.domain.repository.SeriesRepository;
import com.campuspick.demo.domain.repository.UserRepository;
import com.campuspick.demo.dto.SeriesDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
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
    public SeriesDto.SeriesResponseDto getSeriesPosts(String username, String seriesUrl){
        Series series = seriesRepository.findByUserUsernameAndUrl(username, seriesUrl);
        List<Post> posts = postRepository.findAllByUserUsernameAndSeriesUrlOrderBySeriesIndex(username, seriesUrl);
        SeriesDto.SeriesResponseDto responseDto = new SeriesDto.SeriesResponseDto(series, posts);
        return responseDto;
    }

    // 유저 Series 추가
    @Transactional
    public Series addSeries(SeriesDto.SeriesCreateRequestDto requestDto){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityExistsException());

        // url 중복 검사
        String newUrl = requestDto.getUrl();
        if(checkUrlOverlap(newUrl)){
            newUrl = createUrlSlug(newUrl);
        }

        Series series = Series.builder()
                .name(requestDto.getName())
                .url(newUrl)
                .user(user)
                .build();
        return seriesRepository.save(series);
    }

    // url 중복 체크
    public Boolean checkUrlOverlap(String url){
        return seriesRepository.findByUrl(url) != null ? true : false;
    }

    // url 중복 시 urlSlug 생성
    public String createUrlSlug(String url){
        String urlSlug;
        String newUrl;
        do {
            urlSlug = RandomStringUtils.randomAlphanumeric(5);
            newUrl = url+"-"+urlSlug;
        }while(seriesRepository.findByUrl(newUrl) != null);
        return newUrl;
    }
}
