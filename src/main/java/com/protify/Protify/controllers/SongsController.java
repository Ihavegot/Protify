package com.protify.Protify.controllers;

import com.protify.Protify.Request;
import com.protify.Protify.models.Songs;
import com.protify.Protify.service.SongService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    @GetMapping("/songs")
    public Page<Songs> getSongs(@RequestBody @Nullable Request req){
        if(req == null){
            return songService.getSongs(20);
        }
        return songService.getSongs(req.getSize());
    }
    @GetMapping("songs/{id}")
    public Optional<Songs> getSingleSong(@PathVariable long id){
        return songService.getSingleSong(id);
    }
}
