package com.protify.Protify.controllers;

import com.protify.Protify.components.ArtistModelAssembler;
import com.protify.Protify.components.ScoreModelAssembler;
import com.protify.Protify.components.SongsModelAssembler;
import com.protify.Protify.dtos.ArtistDto;
import com.protify.Protify.dtos.ScoreDto;
import com.protify.Protify.dtos.ScorePatchDto;
import com.protify.Protify.models.Artist;
import com.protify.Protify.models.Playlist;
import com.protify.Protify.models.Score;
import com.protify.Protify.models.Songs;
import com.protify.Protify.service.ArtistService;
import com.protify.Protify.service.ScoreService;
import com.protify.Protify.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/score", produces = {MediaTypes.HAL_JSON_VALUE, MediaTypes.HAL_FORMS_JSON_VALUE})
@ExposesResourceFor(Score.class)
public class ScoreController {
    private final ScoreService scoreService;
    private final ScoreModelAssembler scoreModelAssembler;
    private final PagedResourcesAssembler<Score> pagedResourcesAssembler;

    @GetMapping("/songs/{id}")    @Operation(summary="Get Scores for song")
    public PagedModel<EntityModel<Score>> getSingleSongScores(@PathVariable("id") Long id, @ParameterObject Pageable page) {
        Page<Score> scorePage = scoreService.getSingleSongScores(id, page);
        return pagedResourcesAssembler.toModel(scorePage, scoreModelAssembler);
    }

    @GetMapping("/user/{id}")    @Operation(summary="Get Scores for user")
    public PagedModel<EntityModel<Score>> getSingleUserScores(@PathVariable("id") Long id, @ParameterObject Pageable page) {
        Page<Score> scorePage = scoreService.getSingleUserScores(id, page);
        return pagedResourcesAssembler.toModel(scorePage, scoreModelAssembler);
    }

    @PostMapping
    @Operation(summary="Create Score")
    public Score addSingleArtist(@RequestBody ScoreDto scoreDto) {
        return scoreService.addSingleScore(scoreDto);
    }

    @PatchMapping("{id}")
    @Operation(summary="Update Score")
    public ResponseEntity<Score> updateSingleScore(@PathVariable("id") Long id, @RequestBody ScorePatchDto scorePatchDto) throws Exception {
        return scoreService.updateSingleScore(id, scorePatchDto);
    }

    @DeleteMapping("{id}")
    @Operation(summary="Delete Score")
    public ResponseEntity<Score> deleteSingleScore(@PathVariable("id") Long id) {
        scoreService.deleteSingleScore(id);
        return ResponseEntity.ok(null);
    }
}
