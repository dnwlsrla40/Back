package com.campuspick.demo.service;

import com.campuspick.demo.domain.entity.*;
import com.campuspick.demo.domain.repository.*;
import com.campuspick.demo.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostTagRepository postTagRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public PostResponseDto getPost(String username, String postUrl) {
        // Post, User, Series
        Post post = postRepository.findByUserUsernameAndUrl(username, postUrl);
        // Comments
        List<Comment> comments = commentRepository.findByPostIdAndIsRootTrue(post.getId());
        // Tags
        List<PostTag> postTags = postTagRepository.findAllByPostId(post.getId());
        List<Tag> tags = new ArrayList<>();
        for(int i=0; i<postTags.size(); i++){
            tags.add(postTags.get(i).getTag());
        }

        return new PostResponseDto(post, comments, tags);
    }

}
