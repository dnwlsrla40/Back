package com.campuspick.demo.domain.repository;

import com.campuspick.demo.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    Post findByTitle(String title);
    List<Post> findAllByUserId(UUID userId);
    List<Post> findAllByUserUsernameAndSeriesSeriesUrlOrderBySeriesIndex(String username, String url);
    Post findByUserUsernameAndUrl(String username, String url);

}
