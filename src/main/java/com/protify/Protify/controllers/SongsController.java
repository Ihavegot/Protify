package com.protify.Protify.controllers;

import com.protify.Protify.Exceptions.ResourceNotFoundException;
import com.protify.Protify.components.SongsModelAssembler;
import com.protify.Protify.dtos.SongDto;
import com.protify.Protify.dtos.UserDto;
import com.protify.Protify.models.Artist;
import com.protify.Protify.models.Playlist;
import com.protify.Protify.models.Songs;
import com.protify.Protify.models.User;
import com.protify.Protify.repository.SongRepository;
import com.protify.Protify.service.SongService;
import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@ExposesResourceFor(Songs.class)
@RequestMapping(value="/songs", produces = {MediaTypes.HAL_JSON_VALUE, MediaTypes.HAL_FORMS_JSON_VALUE})
@RequiredArgsConstructor
public class SongsController {
    private final SongService songService;
    private final SongsModelAssembler songsModelAssembler;
    private final PagedResourcesAssembler<Songs> pagedResourcesAssembler;

    @GetMapping
    public PagedModel<EntityModel<Songs>> getSongs(@ParameterObject Pageable page, @RequestParam(required = false, name = "page") Integer p,
                                                   @RequestParam(required = false) Integer size,
                                                   @RequestParam(required = false) String[] sort) {
        Page<Songs> songsPage = songService.getSongs(page);
        return pagedResourcesAssembler.toModel(songsPage, songsModelAssembler);
    }

    @GetMapping("{id}")
    public EntityModel<Songs> getSingleSong(@PathVariable("id") Long id) {
        Songs entity = songService.getSingleSong(id);
        return songsModelAssembler.toModel(entity);
    }

    @PostMapping
    public Songs postSong(@RequestBody SongDto song) {
        return songService.postSong(song);
    }

    @PutMapping("{id}")
    public ResponseEntity<Songs> putSong(@PathVariable("id") Long id, @RequestBody SongDto song) throws Exception {
        return songService.putSong(id, song);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Songs> deleteSong(@PathVariable("id") Long id) {
        songService.deleteSong(id);
        return ResponseEntity.ok(null);
    }
}
