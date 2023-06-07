package com.protify.Protify.service;

import com.protify.Protify.Exceptions.ResourceNotFoundException;
import com.protify.Protify.dtos.ArtistDto;
import com.protify.Protify.dtos.ScoreDto;
import com.protify.Protify.dtos.ScorePatchDto;
import com.protify.Protify.models.Artist;
import com.protify.Protify.models.Playlist;
import com.protify.Protify.models.Score;
import com.protify.Protify.models.Songs;
import com.protify.Protify.repository.ArtistRepository;
import com.protify.Protify.repository.ScoreRepository;
import com.protify.Protify.repository.SongRepository;
import com.protify.Protify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScoreService {
    private final ScoreRepository scoreRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;
    public Page<Score> getSingleSongScores(Long id, Pageable page) {
        return scoreRepository.findAllBySongs(id, page);
    }

    public Page<Score> getSingleUserScores(Long id, Pageable page) {
        return scoreRepository.findAllByUser(id, page);
    }

    public Score addSingleScore(ScoreDto scoreDto) {
        Score score = new Score();
        score.setUser(userRepository.getReferenceById(scoreDto.getUserId()));
        score.setSongs(songRepository.getReferenceById(scoreDto.getSongId()));
        return scoreRepository.save(score);
    }

    public ResponseEntity<Score> updateSingleScore(Long id, ScorePatchDto scoreDto) {
        Score updatedScore = scoreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No score with id " + id));
        updatedScore.setScore(scoreDto.getScore());

        scoreRepository.save(updatedScore);
        return ResponseEntity.ok(updatedScore);
    }

    public void deleteSingleScore(long id) {
        scoreRepository.deleteById(id);
    }
}
