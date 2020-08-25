package com.campuspick.demo.service;

import com.campuspick.demo.domain.entity.Post;
import com.campuspick.demo.domain.entity.PostTag;
import com.campuspick.demo.domain.repository.PostTagRepository;
import com.campuspick.demo.dto.TagDetailResponseDto;
import com.campuspick.demo.dto.TagListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TagService {

    private final PostTagRepository postTagRepository;

    @Transactional(readOnly = true)
    public List<TagListResponseDto> getTagList(){

        List<String> tagNames = postTagRepository.findTags();
        List<TagListResponseDto> tags = new ArrayList<>();
        for(int i=0; i<tagNames.size(); i++) {
            int postCnt = postTagRepository.countPostTagByTagName(tagNames.get(i));
            tags.add(new TagListResponseDto(tagNames.get(i), postCnt));
        }
        return tags;
    }

    @Transactional(readOnly = true)
    public TagDetailResponseDto getTagDetail(String tagName) {
        List<PostTag> postTags = postTagRepository.findAllByTagName(tagName);
        int postCnt = postTagRepository.countPostTagByTagName(tagName);
        List<Post> posts = new ArrayList<>();
        for(int i=0; i<postTags.size(); i++){
            posts.add(postTags.get(i).getPost());
        }
        return new TagDetailResponseDto(posts, postCnt);
    }

}
