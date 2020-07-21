package com.campuspick.demo.service;

import com.campuspick.demo.domain.Posts;
import com.campuspick.demo.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    public Posts insertPosts(Posts post){
        return postsRepository.insert(post);
    }

    public List<Posts> getPostsList(String title){
        return postsRepository.findByTitle(title);
    }
}
