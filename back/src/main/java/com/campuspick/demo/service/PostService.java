package com.campuspick.demo.service;

import com.campuspick.demo.domain.entity.*;
import com.campuspick.demo.domain.repository.*;
import com.campuspick.demo.dto.PostDto;
import com.campuspick.demo.dto.SeriesDto;
import com.campuspick.demo.dto.TagDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import java.io.File;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
//    private final SeriesPostRepository seriesPostRepository;
    private final SeriesRepository seriesRepository;
    private final TagRepository tagRepository;
    private final PostTagRepository postTagRepository;
    private final PostImageRepository postImageRepository;

    @Value("${resources.location}")
    private String thumbnailPath;

    @Transactional
    public Post write(PostDto.PostCreateRequestDto requestDto) {

        // 로그인 한 User 정보 가져오기
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityExistsException());

        // post create 시 등록된 series
        SeriesDto.PostCreateSeriesDto postCreateSeriesDto = new SeriesDto.PostCreateSeriesDto(requestDto.getSeries().getName());

        // post create 시 등록된 TagList
        List<TagDto> postCreateTagListDto = requestDto.getTagList();

        // post create 시 등록된 thumbnail 경로
        String thumbnail = requestDto.getThumbnail();

        // post의 url 중복 검사
        String newUrl = requestDto.getUrl();
        if(checkUrlOverlap(newUrl)){
            newUrl = createUrlSlug(newUrl);
        }

        // 생성할 post 작성
        Post post = Post.builder()
                .title(requestDto.getTitle())
                .body(requestDto.getBody())
                .isPrivate(requestDto.getIsPrivate())
                .url(newUrl)
                .shortDescription(requestDto.getShortDescription())
                .user(user)
                .build();

        // 썸네일 경로 가져오기 및 썸네일이 있다면 postImage 추가
        if (thumbnail != null)
            thumbnailExistCheck(thumbnail, user, post);

        // Tag가 있는 지 확인
        tagExistCheck(postCreateTagListDto, post);

        // Series가 있는 지 확인
        seriesExistCheck(postCreateSeriesDto, post);
        
        //post 등록
        return postRepository.save(post);
    }

    // 시리즈를 등록했는 지 확인
    public void seriesExistCheck(SeriesDto.PostCreateSeriesDto postCreateSeriesDto, Post post){
        // series가 있다면 post에 series 등록
        if (postCreateSeriesDto != null && seriesRepository.existsByName(postCreateSeriesDto.getName())) {
            // post의 series 등록
            post.setSeries(seriesRepository.findByName(postCreateSeriesDto.getName()));
            // post의 seriesIndex 등록
            post.setSeriesIndex(postRepository.getMaxSeriesIndex(postCreateSeriesDto.getName())+1);
            if(post.getSeriesIndex() == 1){
                seriesRepository.findByName(postCreateSeriesDto.getName()).setThumbnail(post.getThumbnail());
            }
        }
    }

    // Tag를 등록했는 지 확인
    public void tagExistCheck(List<TagDto> postCreateTagListDto, Post post){
        //Tag가 있다면 TagPost 등록
        if(postCreateTagListDto != null){ ;
            for (TagDto tagDto : postCreateTagListDto){
                // 존재하지 않은 Tag면
                if(tagRepository.findByName(tagDto.getName()) == null){
                    // Tag 추가
                    Tag tag = Tag.builder()
                            .name(tagDto.getName())
                            .build();
                    System.out.println("tag정보");
                    System.out.println(tag.getId());
                    System.out.println(tag.getName());
                    System.out.println("여기까지 실행");
                    tagRepository.save(tag);
                    tagRepository.flush();
                }

                //postTag 생성
                PostTag postTag = PostTag.builder()
                        .tag(tagRepository.findByName(tagDto.getName()))
                        .post(post)
                        .build();

                postTagRepository.save(postTag);
                postTagRepository.flush();
            }
        }
    }

    // 썸네일을 등록했는 지 확인
    public void thumbnailExistCheck(String thumbnail, User user, Post post){
        // 썸네일이 있다면 postImage 추가
        if (thumbnail != null){
            post.setThumbnail(thumbnailPath + thumbnail);
            File file = new File(thumbnailPath + thumbnail);
            PostImage postImage = PostImage.builder()
                    .name(file.getName())
                    .path(file.getPath())
                    .fileSize(file.length())
                    .user(user)
                    .build();
            postImageRepository.save(postImage);
        }
    }

    // url 중복 체크
    public Boolean checkUrlOverlap(String url){
        return postRepository.findByUrl(url) != null ? true : false;
    }

    // url 중복 시 urlSlug 생성
    public String createUrlSlug(String url){
        String urlSlug;
        String newUrl;
        do {
            urlSlug = RandomStringUtils.randomAlphanumeric(5);
            newUrl = url+"-"+urlSlug;
        }while(postRepository.findByUrl(newUrl) != null);
        return newUrl;
    }

}
