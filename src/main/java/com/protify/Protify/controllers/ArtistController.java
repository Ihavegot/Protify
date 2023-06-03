package com.protify.Protify.controllers;

import com.protify.Protify.components.ArtistModelAssembler;
import com.protify.Protify.components.SongsModelAssembler;
import com.protify.Protify.models.Artist;
import com.protify.Protify.models.Playlist;
import com.protify.Protify.models.Songs;
import com.protify.Protify.service.ArtistService;
import com.protify.Protify.service.SongService;
import lombok.RequiredArgsConstructor;
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
public class ArtistController {
    private final ArtistService artistService;
    private final ArtistModelAssembler playlistModelAssembler;
    private final PagedResourcesAssembler<Artist> pagedResourcesAssembler;
    private final PagedResourcesAssembler<Songs> songsPagedResourcesAssembler;
    private final SongService songService;
    private final SongsModelAssembler songsModelAssembler;

    @GetMapping("/artist")
    public PagedModel<EntityModel<Artist>> getArtist(Pageable page){
        Page<Artist> artistPage = artistService.getArtist(page);
        return pagedResourcesAssembler.toModel(artistPage, playlistModelAssembler);
    }

    @GetMapping("/artist/{id}")
    public EntityModel<Artist> getSingleArtist(@PathVariable("id") Artist artist){
        return playlistModelAssembler.toModel(artist);
    }

    @GetMapping("/artist/{id}/songs")
    public PagedModel<EntityModel<Songs>> getSongsByArtist(@PathVariable("id") Long id, Pageable page){
        Page<Songs> songsPage = songService.getSongsByArtist(id, page);
        return songsPagedResourcesAssembler.toModel(songsPage, songsModelAssembler);
    }
}
