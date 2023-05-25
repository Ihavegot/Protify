package com.protify.Protify.controllers;

import com.protify.Protify.components.PlaylistModelAssembler;
import com.protify.Protify.components.SongsModelAssembler;
import com.protify.Protify.models.Playlist;
import com.protify.Protify.models.Songs;
import com.protify.Protify.service.PlaylistService;
import com.protify.Protify.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PlaylistController {
    private final PlaylistService playlistService;
    private final PlaylistModelAssembler playlistModelAssembler;
    private final PagedResourcesAssembler<Playlist> pagedResourcesAssembler;
    private final PagedResourcesAssembler<Songs> songsPagedResourcesAssembler;
    private final SongService songService;
    private final SongsModelAssembler songsModelAssembler;

    @GetMapping("/playlist")
    public PagedModel<EntityModel<Playlist>> getPlaylist(@ParameterObject Pageable page){
        Page<Playlist> playlistPage = playlistService.getPlaylist(page);
        return pagedResourcesAssembler.toModel(playlistPage, playlistModelAssembler);
    }

    @GetMapping("/playlist/{id}")
    public EntityModel<Playlist> getSinglePlaylist(@PathVariable("id") Playlist playlist){
        return playlistModelAssembler.toModel(playlist);
    }

    @GetMapping("/playlist/{id}/song")
    public PagedModel<EntityModel<Songs>> getPlaylistSongs(@PathVariable("id") Long id,@ParameterObject  Pageable page){
        Page<Songs> songsPage = songService.getSongsByPlaylist(id, page);
        return songsPagedResourcesAssembler.toModel(songsPage, songsModelAssembler);
    }
}
