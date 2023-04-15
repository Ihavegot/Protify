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
import org.springframework.web.bind.annotation.*;

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
    public PagedModel<SongsModel> getSongs(@RequestParam @Nullable Optional<Integer> size, @RequestParam @Nullable Optional<Integer> page){
        Page<Songs> songsPage = songService.getSongs(page.orElse(0), size.orElse(20));
        return pagedResourcesAssembler.toModel(songsPage, songsModelAssembler);
    }
    @GetMapping("songs/{id}")
    public Optional<Songs> getSingleSong(@PathVariable long id){
        return songService.getSingleSong(id);
    }
}
