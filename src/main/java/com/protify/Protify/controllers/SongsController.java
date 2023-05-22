package com.protify.Protify.controllers;

import com.protify.Protify.components.SongsModelAssembler;
import com.protify.Protify.models.Songs;
import com.protify.Protify.service.SongService;
import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SongsController {
    private final SongService songService;
    private final SongsModelAssembler songsModelAssembler;
    private final PagedResourcesAssembler<Songs> pagedResourcesAssembler;

    @GetMapping("/songs")

    public PagedModel<EntityModel<Songs>> getSongs(@ParameterObject Pageable page){
        Page<Songs> songsPage = songService.getSongs(page);
        return pagedResourcesAssembler.toModel(songsPage, songsModelAssembler);
    }

    @GetMapping("/songs/{id}")
    public EntityModel<Songs> getSingleSong(@PathVariable("id") Songs songs){
        return songsModelAssembler.toModel(songs);
    }
}
