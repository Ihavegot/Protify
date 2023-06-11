package com.protify.Protify.repository;

import com.protify.Protify.embeddable.ScoreKey;
import com.protify.Protify.models.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score, ScoreKey> {
}
