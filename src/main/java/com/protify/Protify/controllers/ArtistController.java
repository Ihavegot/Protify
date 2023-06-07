package com.protify.Protify.controllers;

import com.protify.Protify.components.ArtistModelAssembler;
import com.protify.Protify.components.SongsModelAssembler;
import com.protify.Protify.models.Artist;
import com.protify.Protify.models.Playlist;
import com.protify.Protify.models.Songs;
import com.protify.Protify.service.ArtistService;
import com.protify.Protify.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/artist", produces = {MediaTypes.HAL_JSON_VALUE, MediaTypes.HAL_FORMS_JSON_VALUE})
@ExposesResourceFor(Artist.class)
public class ArtistController {
    private final ArtistService artistService;
    private final ArtistModelAssembler artistModelAssembler;
    private final PagedResourcesAssembler<Artist> pagedResourcesAssembler;
    private final PagedResourcesAssembler<Songs> songsPagedResourcesAssembler;
    private final SongService songService;
    private final SongsModelAssembler songsModelAssembler;

    @GetMapping
    @Operation(summary="Get Artist list")
    public PagedModel<EntityModel<Artist>> getArtists(@ParameterObject Pageable page) {
        Page<Artist> artistPage = artistService.getArtist(page);
        return pagedResourcesAssembler.toModel(artistPage, artistModelAssembler);
    }

    @GetMapping("{id}")    @Operation(summary="Get Artist")
    public EntityModel<Artist> getSingleArtist(@PathVariable("id") Long id) {
        Artist artist = artistService.getSingleArtist(id);
        return artistModelAssembler.toModel(artist);
    }

    @GetMapping("{id}/songs")
    @Operation(summary="Get Artist's Song list")
    public PagedModel<EntityModel<Songs>> getSongsByArtist(@PathVariable("id") Long id, @ParameterObject Pageable page) {
        Page<Songs> songsPage = songService.getSongsByArtist(id, page);
        return songsPagedResourcesAssembler.toModel(songsPage, songsModelAssembler);
    }

    @PostMapping
    @Operation(summary="Create Artist")
    public Artist addSingleArtist(@RequestBody Artist artist) {
        return artistService.addSingleArtist(artist);
    }

    @PutMapping
    @Operation(summary="Update Artist")
    public ResponseEntity<Artist> updateSingleArtist(@RequestBody Artist artist) {
        return ResponseEntity.of(artistService.updateSingleArtist(artist));
    }

    @DeleteMapping("{id}")
    @Operation(summary="Delete Artist")
    public ResponseEntity<Artist> deleteSingleArtist(@PathVariable("id") Long id) {
        artistService.deleteSingleArtist(id);
        return ResponseEntity.ok(null);
    }
}
