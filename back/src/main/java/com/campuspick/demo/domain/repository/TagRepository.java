package com.campuspick.demo.domain.repository;

import com.campuspick.demo.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {

    Tag findByName(String tagname);

}
