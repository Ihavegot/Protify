package com.protify.Protify.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.protify.Protify.models.Artist;
import com.protify.Protify.models.Playlist;
import com.protify.Protify.models.Songs;
import com.protify.Protify.models.User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.LinkRelationProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

@RequiredArgsConstructor
public class HomeController {
    private final EntityLinks links;
    private final LinkRelationProvider linkRelationProvider;
    
    @GetMapping(value = "/", produces = {MediaTypes.HAL_JSON_VALUE, MediaTypes.HAL_FORMS_JSON_VALUE})
    @Operation(summary="Get Link list")
    public RepresentationModel<?> homeEndpoint() {
        return RepresentationModel.of(null,
                List.of(
                        linkTo(methodOn(SongsController.class)
                                .getSongs(null, null, null, null))
                                .withRel(linkRelationProvider.getCollectionResourceRelFor(Songs.class)),
                        linkTo(methodOn(PlaylistController.class)
                                .getPlaylist(null, null, null, null))
                                .withRel(linkRelationProvider.getCollectionResourceRelFor(Playlist.class)),
                        linkTo(methodOn(UserController.class)
                                .getUser(null, null, null, null))
                                .withRel(linkRelationProvider.getCollectionResourceRelFor(User.class)),
                        linkTo(methodOn(ArtistController.class)
                                .getArtists(null, null, null, null))
                                .withRel(linkRelationProvider.getCollectionResourceRelFor(Artist.class))
                )
        );
    }
}
