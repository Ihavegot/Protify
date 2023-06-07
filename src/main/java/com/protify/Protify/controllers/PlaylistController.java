package com.protify.Protify.controllers;

import com.protify.Protify.components.PlaylistModelAssembler;
import com.protify.Protify.components.SongsModelAssembler;
import com.protify.Protify.dtos.PlaylistDto;
import com.protify.Protify.dtos.PlaylistSongsDto;
import com.protify.Protify.models.Playlist;
import com.protify.Protify.models.Songs;
import com.protify.Protify.service.PlaylistService;
import com.protify.Protify.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping(value ="/playlists", produces = {MediaTypes.HAL_JSON_VALUE, MediaTypes.HAL_FORMS_JSON_VALUE})
@RequiredArgsConstructor
public class PlaylistController {
    private final PlaylistService playlistService;
    private final PlaylistModelAssembler playlistModelAssembler;
    private final PagedResourcesAssembler<Playlist> pagedResourcesAssembler;
    private final PagedResourcesAssembler<Songs> songsPagedResourcesAssembler;
    private final SongService songService;
    private final SongsModelAssembler songsModelAssembler;

    @GetMapping
    @Operation(summary="Get Playlist list")
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
    @Operation(summary="Get Playlist")
    public EntityModel<Playlist> getSinglePlaylist(@PathVariable("id") Long id){
        Playlist playlist = playlistService.getSinglePlaylist(id);
        return playlistModelAssembler.toModel(playlist);
    }

    @GetMapping("{id}/songs")
    @Operation(summary="Get Playlist's Song list")
    public PagedModel<EntityModel<Songs>> getPlaylistSongs(@PathVariable("id") Long id, @ParameterObject Pageable page, @RequestParam(required = false, name = "page") Integer p,
                                                           @RequestParam(required = false) Integer size,
                                                           @RequestParam(required = false) String[] sort) {
        Page<Songs> songsPage = songService.getSongsByPlaylist(id, page);
        PagedModel<EntityModel<Songs>> songPage = songsPagedResourcesAssembler.toModel(songsPage, songsModelAssembler);
        songPage.getContent().forEach((song) ->song.mapLink(IanaLinkRelations.SELF, link -> link.andAffordance(afford(methodOn(PlaylistController.class).deleteSongFromPlaylist(null)))));
        return songPage;
    }

    @PostMapping
    @Operation(summary="Create Playlist")
    public Playlist addSinglePlaylist(@RequestBody PlaylistDto playlist){
        return playlistService.addSinglePlaylist(playlist);
    }

    @PatchMapping("{id}/{title}")
    @Operation(summary="Update Playlist")
    public ResponseEntity<Playlist> updateSinglePlaylist(@PathVariable("id") Long id,@PathVariable("title") String title) {
        return ResponseEntity.of(playlistService.updateSinglePlaylist(id, title));
    }

    @DeleteMapping("/songs/delete")
    @Operation(summary="From Playlist delete Song")
    public ResponseEntity<Playlist> deleteSongFromPlaylist(@RequestBody PlaylistSongsDto playlist) {
        return ResponseEntity.of(playlistService.deleteSongFromPlaylist(playlist));
    }

    @PostMapping("/songs/add")
    @Operation(summary="To Playlist add Song")
    public ResponseEntity<Playlist> addSongToPlaylist(@RequestBody PlaylistSongsDto playlist) {
        return ResponseEntity.of(playlistService.addSongToPlaylist(playlist));
    }

    @DeleteMapping("{id}")
    @Operation(summary="Delete Playlist")
    public ResponseEntity<Playlist> deleteSinglePlaylist(@PathVariable("id") Long id) {
        playlistService.deleteSinglePlaylist(id);
        return ResponseEntity.ok(null);
    }
}
