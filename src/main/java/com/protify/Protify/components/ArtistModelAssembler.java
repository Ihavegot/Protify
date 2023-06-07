package com.protify.Protify.components;

import com.protify.Protify.controllers.ArtistController;
import com.protify.Protify.controllers.PlaylistController;
import com.protify.Protify.controllers.SongsController;
import com.protify.Protify.dtos.ArtistDto;
import com.protify.Protify.dtos.SongDto;
import com.protify.Protify.models.Artist;
import com.protify.Protify.models.Songs;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.afford;

@Component
public class ArtistModelAssembler implements RepresentationModelAssembler<Artist, EntityModel<Artist>> {
    @Override
    public EntityModel<Artist> toModel(Artist entity) {
        try {
            return EntityModel.of(entity).add(
                    linkTo(methodOn(ArtistController.class).getSingleArtist(entity.getId())).withSelfRel()
                            .andAffordance(afford(methodOn(ArtistController.class).deleteSingleArtist(entity.getId())))
                            .andAffordance(afford(methodOn(ArtistController.class).updateSingleArtist(entity.getId(), new ArtistDto())))
                    ,
                    linkTo(methodOn(ArtistController.class).getSongsByArtist(entity.getId(), null)).withRel("songs")
            );
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
