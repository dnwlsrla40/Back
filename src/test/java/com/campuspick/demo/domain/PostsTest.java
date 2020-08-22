package com.campuspick.demo.domain;

import com.campuspick.demo.repository.PostsRepository;
import com.campuspick.demo.service.PostsService;
import com.jayway.jsonpath.Criteria;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import javax.management.Query;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostsTest {
    @Autowired
    PostsService postsService;

    @Test
    public void insertTest(){
        Posts posts = Posts.builder()
                .author("test Author2")
                .context("test Context2")
                .title("test Title2")
                .build();

        ObjectId objectId = postsService.insertPosts(posts).getId();

        System.out.println(objectId);

        List<Posts> postsList = postsService.getPostsList("test Title2");

        Posts findpost =postsList.get(0);

        assertThat(findpost.getAuthor()).isEqualTo(posts.getAuthor());
        assertThat(findpost.getContext()).isEqualTo(posts.getContext());
        assertThat(findpost.getTitle()).isEqualTo(posts.getTitle());
    }

}
