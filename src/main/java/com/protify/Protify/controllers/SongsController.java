package com.protify.Protify.controllers;

import com.protify.Protify.models.Songs;
import com.protify.Protify.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SongsController {
    private final SongService songService;
    @GetMapping("/songs")
    public List<Songs> getSongs(){
        return songService.getSongs();
    }
}
