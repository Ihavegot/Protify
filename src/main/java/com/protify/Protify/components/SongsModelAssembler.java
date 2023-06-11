package com.protify.Protify.components;

import com.protify.Protify.controllers.PlaylistController;
import com.protify.Protify.controllers.SongsController;
import com.protify.Protify.controllers.UserController;
import com.protify.Protify.dtos.ScoredSongDto;
import com.protify.Protify.dtos.SongDto;
import com.protify.Protify.mappers.SongMapper;
import com.protify.Protify.models.Artist;
import com.protify.Protify.models.Playlist;
import com.protify.Protify.models.Songs;
import com.protify.Protify.models.User;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.mediatype.hal.HalModelBuilder;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.LinkRelationProvider;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.Collection;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SongsModelAssembler implements RepresentationModelAssembler<Songs, EntityModel<ScoredSongDto>> {
    private final EntityLinks links;
    private final LinkRelationProvider linkRelationProvider;
    @Override
    public EntityModel<Songs> toModel(Songs entity) {
        HalModelBuilder builder;
        try {
            builder = HalModelBuilder.halModelOf(entity)
                    .link(links.linkToItemResource(entity, Songs::getId)
                            .andAffordance(afford(methodOn(SongsController.class).deleteSong(entity.getId())))
                            .andAffordance(afford(methodOn(SongsController.class).putSong(entity.getId(), new SongDto()))
                    ));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (entity.getArtist() != null) {

            builder = builder
                    .preview(entity.getArtist())
                    .forLink(links.linkToItemResource(entity.getArtist(), Artist::getId).withRel(linkRelationProvider.getItemResourceRelFor(Artist.class)))
        ;}
        return (EntityModel<Songs>) builder.build();
    }
}
