package com.campuspick.demo.domain.repository;

import com.campuspick.demo.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    Post findByTitle(String title);
    List<Post> findAllByUserId(UUID userId);
    List<Post> findAllByUserUsernameAndSeriesUrlOrderBySeriesIndex(String username, String url);
    Post findByUserUsernameAndUrl(String username, String url);
    Post findByUrl(String url);

    //(시리즈가 같은 post 중 indexSeries가 제일 큰 것을 찾는 쿼리
    @Query("select max(p.seriesIndex) from Post p where p.series.name = ?1")
    Integer getMaxSeriesIndex(String seriesName);

}
