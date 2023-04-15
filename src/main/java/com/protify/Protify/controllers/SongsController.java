package com.protify.Protify.controllers;

import com.protify.Protify.Request;
import com.protify.Protify.SongsModel;
import com.protify.Protify.components.SongsModelAssembler;
import com.protify.Protify.models.Songs;
import com.protify.Protify.service.SongService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class SongsController {
    private final SongService songService;
    private final SongsModelAssembler songsModelAssembler;
    private final PagedResourcesAssembler<Songs> pagedResourcesAssembler;
    @GetMapping("/songs")
    public PagedModel<SongsModel> getSongs(@RequestBody @Nullable Request req){
        Page<Songs> songsPage;
        if(req == null){
            songsPage = songService.getSongs(20);
        }else {
            songsPage = songService.getSongs(req.getSize());
        }
        return pagedResourcesAssembler.toModel(songsPage, songsModelAssembler);
    }
    @GetMapping("songs/{id}")
    public Optional<Songs> getSingleSong(@PathVariable long id){
        return songService.getSingleSong(id);
    }
}
