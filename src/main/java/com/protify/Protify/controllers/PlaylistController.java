package com.protify.Protify.controllers;

import com.protify.Protify.components.PlaylistModelAssembler;
import com.protify.Protify.components.SongsModelAssembler;
import com.protify.Protify.dtos.PlaylistDto;
import com.protify.Protify.models.Playlist;
import com.protify.Protify.models.Songs;
import com.protify.Protify.service.PlaylistService;
import com.protify.Protify.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.afford;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@ExposesResourceFor(Playlist.class)
@RequestMapping("/playlists")
@RequiredArgsConstructor
public class PlaylistController {
    private final PlaylistService playlistService;
    private final PlaylistModelAssembler playlistModelAssembler;
    private final PagedResourcesAssembler<Playlist> pagedResourcesAssembler;
    private final PagedResourcesAssembler<Songs> songsPagedResourcesAssembler;
    private final SongService songService;
    private final SongsModelAssembler songsModelAssembler;

    @GetMapping
    public CollectionModel<EntityModel<Playlist>> getPlaylist(@ParameterObject Pageable page, @RequestParam(required = false, name = "page") Integer p,
                                                              @RequestParam(required = false) Integer size,
                                                              @RequestParam(required = false) String[] sort
    ) {

        Page<Playlist> playlistPage = playlistService.getPlaylist(page);
        return pagedResourcesAssembler.toModel(playlistPage, playlistModelAssembler)
                .mapLink(IanaLinkRelations.SELF, link->link.andAffordance(
                        afford(methodOn(PlaylistController.class).addSinglePlaylist(null))
                        )
                        );
    }

    @GetMapping("{id}")
    public EntityModel<Playlist> getSinglePlaylist(@PathVariable("id") Long id){
        Playlist playlist = playlistService.getSinglePlaylist(id);
        return playlistModelAssembler.toModel(playlist);
    }

    @GetMapping("{id}/songs")
    public PagedModel<EntityModel<Songs>> getPlaylistSongs(@PathVariable("id") Long id, @ParameterObject Pageable page, @RequestParam(required = false, name = "page") Integer p,
                                                           @RequestParam(required = false) Integer size,
                                                           @RequestParam(required = false) String[] sort) {
        Page<Songs> songsPage = songService.getSongsByPlaylist(id, page);
        PagedModel<EntityModel<Songs>> songPage = songsPagedResourcesAssembler.toModel(songsPage, songsModelAssembler);
        songPage.getContent().forEach((song) ->song.mapLink(IanaLinkRelations.SELF, link -> link.andAffordance(afford(methodOn(PlaylistController.class).deleteSongFromPlaylist(id, song.getContent().getId())))));
        return songPage;
    }

    @PostMapping
    public Playlist addSinglePlaylist(@RequestBody PlaylistDto playlist){
        return playlistService.addSinglePlaylist(playlist);
    }

    @PatchMapping("{id}/{title}")
    public ResponseEntity<Playlist> updateSinglePlaylist(@PathVariable("id") Long id,@PathVariable("title") String title) {
        return ResponseEntity.of(playlistService.updateSinglePlaylist(id, title));
    }

    @PatchMapping("{id}/songs/{songId}/delete")
    public ResponseEntity<Playlist> deleteSongFromPlaylist(@PathVariable("id") Long id, @PathVariable("songId") Long songId) {
        return ResponseEntity.of(playlistService.deleteSongFromPlaylist(id, songId));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Playlist> deleteSinglePlaylist(@PathVariable("id") Long id) {
        playlistService.deleteSinglePlaylist(id);
        return ResponseEntity.ok(null);
    }
}
