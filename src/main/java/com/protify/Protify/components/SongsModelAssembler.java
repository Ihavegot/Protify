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
    public EntityModel<ScoredSongDto> toModel(Songs entity) {




        Link self = links.linkToItemResource(entity, Songs::getId);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<String> authorities = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();


        if(authorities.contains("ROLE_USER")){
            self = self             .andAffordance(afford(methodOn(SongsController.class).putSongsScore(entity.getId(), null)));
        }

        if( authorities.contains("ROLE_ADMIN")){

                self = self             .andAffordance(afford(methodOn(SongsController.class).deleteSong(entity.getId())))
                        .andAffordance(afford(methodOn(SongsController.class).putSong(entity.getId(), new SongDto())));

        }

        ScoredSongDto model = Mappers.getMapper(SongMapper.class).songToScoredSong(entity, entity.getScores().stream().filter(
                score-> Objects.equals(score.getUser().getLogin(), auth.getName())
        ).findFirst().orElse(null));

        HalModelBuilder
                builder = HalModelBuilder.halModelOf(model).link(self);


        if (entity.getArtist() != null) {

            builder = builder
                    .preview(entity.getArtist())
                    .forLink(links.linkToItemResource(entity.getArtist(), Artist::getId).withRel(linkRelationProvider.getItemResourceRelFor(Artist.class)))
        ;}

        return (EntityModel<ScoredSongDto>) builder.build();
    }
}
