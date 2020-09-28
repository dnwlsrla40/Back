package com.campuspick.demo.domain.repository;

import com.campuspick.demo.domain.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SeriesRepository extends JpaRepository <Series, UUID> {

    List<Series> findAllByUserUsername(String username);
    Series findByUserUsernameAndSeriesUrl(String username, String url);

}
