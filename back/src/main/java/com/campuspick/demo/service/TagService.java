package com.campuspick.demo.service;

import com.campuspick.demo.domain.entity.Post;
import com.campuspick.demo.domain.entity.PostTag;
import com.campuspick.demo.domain.entity.Tag;
import com.campuspick.demo.domain.repository.PostTagRepository;
import com.campuspick.demo.domain.repository.TagRepository;
import com.campuspick.demo.dto.TagDetailResponseDto;
import com.campuspick.demo.dto.TagListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TagService {

    private final PostTagRepository postTagRepository;
    private final TagRepository tagRepository;

    @Transactional(readOnly = true)
    public List<TagListResponseDto> getTagList(String sort, int page) {

        PageRequest pageRequest = PageRequest.of(page, 40, Sort.by("name"));
        List<Tag> pagedTags = tagRepository.findAll(pageRequest).getContent();
        List<TagListResponseDto> tags = new ArrayList<>();

        for (int i = 0; i < pagedTags.size(); i++) {
            String tagName = pagedTags.get(i).getName();
            int postCnt = postTagRepository.countPostTagByTagName(tagName);
            tags.add(new TagListResponseDto(tagName, postCnt));
        }

        if (sort.equals("trending")) {
            tags.sort((o1, o2) -> {
                if (o1.getPostCnt() == o2.getPostCnt()) return 0;
                else if (o1.getPostCnt() < o2.getPostCnt()) return 1;
                else return -1;
            });
            return tags;
        } else if (sort.equals("alphabetical")) return tags;
        else return null;
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
