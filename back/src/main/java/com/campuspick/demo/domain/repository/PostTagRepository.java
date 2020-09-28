package com.campuspick.demo.domain.repository;

import com.campuspick.demo.domain.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag, UUID> {

    // 이 태그를 가진 포스트의 개수
    int countPostTagByTagName(String tagName);

    // 모든 태그
    @Query(value = "select tag.name from tag " +
            "inner join post_tag on tag.id=tag_id " +
            "inner join post on post.id=post_id " +
            "GROUP BY tag_id " +
            "ORDER BY count(post_id) DESC",
            nativeQuery = true)
    List<String> findTags();

    // 이 태그를 가진 모든 포스트
    List<PostTag> findAllByTagName(String tagName);
    List<PostTag> findAllByPostId(UUID pid);

    List<PostTag> findByPostIdIn(List<UUID> postIds);
    List<PostTag> findByTagNameAndPostIdIn(String tagname, List<UUID> postIds);



}
