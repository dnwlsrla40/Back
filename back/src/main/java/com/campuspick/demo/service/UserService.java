package com.campuspick.demo.service;

import com.campuspick.demo.domain.entity.Post;
import com.campuspick.demo.domain.entity.PostTag;
import com.campuspick.demo.domain.entity.User;
import com.campuspick.demo.domain.repository.PostRepository;
import com.campuspick.demo.domain.repository.PostTagRepository;
import com.campuspick.demo.domain.repository.UserRepository;
import com.campuspick.demo.dto.RegisterDto;
import com.campuspick.demo.dto.UserVelogResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.sql.Blob;
import java.util.*;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostTagRepository postTagRepository;

    @Transactional
    public User save(RegisterDto registerDto){
        User user = User.builder()
                .email(registerDto.getEmail())
                .username(registerDto.getUsername())
                .shortBio(registerDto.getShortBio())
                .build();
        return userRepository.save(user);
    }

    @Transactional
    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityExistsException());
    }

    @Transactional(readOnly = true)
    public User getUserInfo(UUID id){
        User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return user;
    }

    @Transactional
    public User updateUserThumbnail(UUID id, Blob thumbnail){
        User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        user.setThumbnail(thumbnail);
        return user;
    }

    @Transactional
    public User deleteUserThumbnail(UUID id){
        User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        user.setThumbnail(null);
        return user;
    }

    @Transactional
    public User updateUserShortBio(UUID id, String shortBio){
        User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        user.setShortBio(shortBio);
        return user;
    }

    @Transactional
    public User updateUserVelogName(UUID id, String velogName){
        User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        user.setVelogName(velogName);
        return user;
    }

    @Transactional
    public User updateUserProfileLinks(UUID id, String profileLinks){
        User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        user.setProfileLinks(profileLinks);
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
        user.setAbout(about);
        return user;
    }

}
