package com.campuspick.demo.domain.repository;

import com.campuspick.demo.domain.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, UUID> {

}
