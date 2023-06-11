package com.protify.Protify.repository;

import com.protify.Protify.models.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    @Query("select avg(score) from Score where songs.id = :#{#id}")
    float findAvgScore(@Param("id") Long id);
}
