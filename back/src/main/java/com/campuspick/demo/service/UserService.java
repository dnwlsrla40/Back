package com.campuspick.demo.service;

import com.campuspick.demo.domain.entity.Post;
import com.campuspick.demo.domain.entity.PostTag;
import com.campuspick.demo.domain.entity.User;
import com.campuspick.demo.domain.repository.PostRepository;
import com.campuspick.demo.domain.repository.PostTagRepository;
import com.campuspick.demo.domain.repository.UserRepository;
import com.campuspick.demo.dto.UserUpdateRequestDto;
import com.campuspick.demo.dto.UserVelogResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostTagRepository postTagRepository;

    @Transactional(readOnly = true)
    public User getUserInfo(UUID id){
        User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return user;
    }

    @Transactional
    public User updateUserInfo(UUID id, UserUpdateRequestDto requestDto){
        User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        user.updateUserInfo(requestDto.getDisplayName(), requestDto.getShortBio(),
                requestDto.getThumbnail(), requestDto.getProfileLinks());
        return user;
    }

    @Transactional
    public void deleteUser(UUID id){
        User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public UserVelogResponseDto getUserVelog(String username, String tagname){
        User user = userRepository.findByUsername(username);
        List<Post> posts = postRepository.findAllByUserId(user.getId());
        List<UUID> postIds = new ArrayList<>();
        for(int i=0; i<posts.size(); i++){
            postIds.add(posts.get(i).getId());
        }

        List<PostTag> postTags;
        if(tagname == null) postTags = postTagRepository.findByPostIdIn(postIds);
        else postTags = postTagRepository.findByTagNameAndPostIdIn(tagname, postIds);

        return new UserVelogResponseDto(user, postTags);
    }

    @Transactional(readOnly = true)
    public User getUserInfo(String username){
        User user = userRepository.findByUsername(username);
        return user;
    }

    @Transactional
    public User updateUserAbout(String username, String about){
        User user = userRepository.findByUsername(username);
        user.updateUserAbout(about);
        return user;
    }

}
