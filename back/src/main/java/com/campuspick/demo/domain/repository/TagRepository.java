package com.campuspick.demo.domain.repository;

import com.campuspick.demo.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {

    Tag findByName(String tagname);

    // 해당 Tag가 존재하는 지 검색
    @Query("select case when count(s)>0 then true else false end from Series s where s.name = ?1")
    boolean existsByName(String name);
}
