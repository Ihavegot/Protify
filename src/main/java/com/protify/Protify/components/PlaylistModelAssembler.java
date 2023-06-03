package com.protify.Protify.components;

import com.protify.Protify.controllers.PlaylistController;
import com.protify.Protify.models.Playlist;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import javax.swing.text.html.parser.Entity;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PlaylistModelAssembler implements RepresentationModelAssembler<Playlist, EntityModel<Playlist>> {
    @Override
    public EntityModel<Playlist> toModel(Playlist entity) {
        return EntityModel.of(entity).add(
                linkTo(methodOn(PlaylistController.class).getSinglePlaylist(entity)).withSelfRel()
        ).add(linkTo(methodOn(PlaylistController.class).getPlaylistSongs(entity.getId(),null,null,null,null)).withRel("songs"));
    }
}
