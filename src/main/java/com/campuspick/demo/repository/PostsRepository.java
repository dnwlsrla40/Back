package com.campuspick.demo.repository;

import com.campuspick.demo.domain.Posts;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsRepository extends MongoRepository<Posts, String> {
    List<Posts> findByTitle(String title);
}
