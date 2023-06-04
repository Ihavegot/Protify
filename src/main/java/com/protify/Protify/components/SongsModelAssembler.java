package com.protify.Protify.components;

import com.protify.Protify.controllers.PlaylistController;
import com.protify.Protify.controllers.SongsController;
import com.protify.Protify.models.Artist;
import com.protify.Protify.models.Playlist;
import com.protify.Protify.models.Songs;
import com.protify.Protify.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.mediatype.hal.HalModelBuilder;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.LinkRelationProvider;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class SongsModelAssembler implements RepresentationModelAssembler<Songs, EntityModel<Songs>> {



    @Override
    public EntityModel<Songs> toModel(Songs entity) {






        HalModelBuilder builder = HalModelBuilder.halModelOf(entity)
                .link(linkTo(methodOn(SongsController.class).getSingleSong(entity)).withSelfRel())
         ;


        if(entity.getArtist() != null){

            builder = builder
                    //            TODO: replace `embed` with `preview`
                    .embed(entity.getArtist())

//                    .forLink( links.linkToItemResource(entity.getArtist(), Artist::getId) .withRel( linkRelationProvider.getItemResourceRelFor(Artist.class)))
            ;
        }

        return (EntityModel<Songs>)builder
                .build();
    }
}
