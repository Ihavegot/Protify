package com.protify.Protify.controllers;

import com.protify.Protify.components.SongsModelAssembler;
import com.protify.Protify.dtos.ScoredSongDto;
import com.protify.Protify.dtos.SongDto;
import com.protify.Protify.dtos.SongScoreDto;
import com.protify.Protify.embeddable.ScoreKey;
import com.protify.Protify.models.Score;
import com.protify.Protify.models.Songs;
import com.protify.Protify.models.User;
import com.protify.Protify.repository.UserRepository;
import com.protify.Protify.service.ScoreService;
import com.protify.Protify.service.SongService;
import com.protify.Protify.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@SecurityRequirement(name = "security_auth")
@ExposesResourceFor(Songs.class)
@RequestMapping(value = "/songs", produces = {MediaTypes.HAL_JSON_VALUE, MediaTypes.HAL_FORMS_JSON_VALUE})
@RequiredArgsConstructor
public class SongsController {
    private final SongService songService;
    private final SongsModelAssembler songsModelAssembler;
    private final PagedResourcesAssembler<Songs> pagedResourcesAssembler;
    @NonNull
    private final UserService userService;
    @NonNull
    private ScoreService scoreService;

    @GetMapping
    @Operation(summary = "Song list")
    public PagedModel<EntityModel<ScoredSongDto>> getSongs(@ParameterObject Pageable page, @RequestParam(required = false, name = "page") Integer p,
                                                           @RequestParam(required = false) Integer size,
                                                           @RequestParam(required = false) String[] sort) {
        Page<Songs> songsPage = songService.getSongs(page);
        return pagedResourcesAssembler.toModel(songsPage, songsModelAssembler);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get Song")
    public EntityModel<ScoredSongDto> getSingleSong(@PathVariable("id") Long id) {
        Songs entity = songService.getSingleSong(id);
        return songsModelAssembler.toModel(entity);
    }

    @PostMapping
    @Operation(summary = "Create Song")
    public Songs postSong(@RequestBody SongDto song) {
        return songService.postSong(song);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update Song")
    public ResponseEntity<Songs> putSong(@PathVariable("id") Long id, @RequestBody SongDto songDto) {
        Songs song = songService.putSong(id, songDto);
        return ResponseEntity.ok(song);
    }

    @PutMapping("{id}/score")
    @Operation(summary = "Update Song's Score")
    public ResponseEntity<EntityModel<ScoredSongDto>> putSongsScore(@PathVariable("id") Long id, @RequestBody SongScoreDto scoreDto) {
        Songs song = songService.getSingleSong(id);
        User user = userService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();

        scoreService.save(
                Score.builder().id(ScoreKey.builder().userId(user.getId()).songId(song.getId()).build()).user(
                        user
                ).score(scoreDto.getScore()).songs(song).build()
        );


        return ResponseEntity.ok(songsModelAssembler.toModel(songService.save(song)));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete Song")
    public ResponseEntity<EntityModel<ScoredSongDto>> deleteSong(@PathVariable("id") Long id) {
        var song = songService.getSingleSong(id);
        songService.deleteSong(id);
        return ResponseEntity.ok(songsModelAssembler.toModel(song));
    }
}
