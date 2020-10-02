package com.campuspick.demo.domain.repository;

import com.campuspick.demo.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

    Comment findByText(String text);

    List<Comment> findByPostIdAndIsRootTrue(UUID postId);

}
