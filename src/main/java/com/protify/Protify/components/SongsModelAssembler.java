package com.protify.Protify.components;

import com.protify.Protify.controllers.SongsController;
import com.protify.Protify.models.Songs;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SongsModelAssembler implements RepresentationModelAssembler<Songs, EntityModel<Songs>> {
    @Override
    public EntityModel<Songs> toModel(Songs entity) {
        return EntityModel.of(entity).add(
                linkTo(methodOn(SongsController.class).getSingleSong(entity)).withSelfRel()
        );
    }
}
