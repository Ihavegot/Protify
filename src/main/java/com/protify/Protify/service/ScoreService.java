package com.protify.Protify.service;

import com.protify.Protify.models.Score;
import com.protify.Protify.repository.ScoreRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class ScoreService {

    @NonNull
    private  final ScoreRepository scoreRepository;


    public void save(Score build) {
        scoreRepository.save(build);
    }
}
