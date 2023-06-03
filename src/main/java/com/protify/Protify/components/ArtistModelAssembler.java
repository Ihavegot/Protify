package com.protify.Protify.components;

import com.protify.Protify.controllers.ArtistController;
import com.protify.Protify.controllers.SongsController;
import com.protify.Protify.models.Artist;
import com.protify.Protify.models.Songs;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ArtistModelAssembler implements RepresentationModelAssembler<Artist, EntityModel<Artist>> {
    @Override
    public EntityModel<Artist> toModel(Artist entity) {
        return EntityModel.of(entity).add(
                linkTo(methodOn(ArtistController.class).getSingleArtist(entity)).withSelfRel(),
                linkTo(methodOn(ArtistController.class).getSongsByArtist(entity.getId(), null)).withRel("songs")
        );
    }
}
