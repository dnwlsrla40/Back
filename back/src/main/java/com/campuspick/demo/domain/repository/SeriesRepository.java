package com.campuspick.demo.domain.repository;

import com.campuspick.demo.domain.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SeriesRepository extends JpaRepository <Series, UUID> {

    List<Series> findAllByUserUsername(String username);
    Series findByUserUsernameAndUrl(String username, String url);

    @Query("select s from Series s where s.user.email = ?1 order by s.name asc")
    List<Series> findAllByEmail(String email);

    @Query("select case when count(s)>0 then true else false end from Series s where s.name = ?1")
    boolean existsByName(String name);

    Series findByUrl(String url);
    Series findByName(String name);
}
