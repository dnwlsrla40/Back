package com.campuspick.demo.domain.repository;

import com.campuspick.demo.domain.entity.PostHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostHistoryRepository extends JpaRepository<PostHistory, UUID> {
    List<PostHistory> findAllByUserId(UUID userId);
    Optional<PostHistory> findById(UUID id);
}
