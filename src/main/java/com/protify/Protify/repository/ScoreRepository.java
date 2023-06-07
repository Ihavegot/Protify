package com.protify.Protify.repository;

import com.protify.Protify.models.Playlist;
import com.protify.Protify.models.Score;
import com.protify.Protify.models.Songs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    Page<Score> findAllBySongsId(Long id, Pageable page);
    Page<Score> findAllByUserId(Long id,Pageable page);
}