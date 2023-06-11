package com.protify.Protify.components;

import com.protify.Protify.controllers.ArtistController;
import com.protify.Protify.dtos.ArtistDto;
import com.protify.Protify.models.Artist;

import com.protify.Protify.models.Playlist;
import com.protify.Protify.models.Songs;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.afford;

import java.util.Collection;

@Component
public class ArtistModelAssembler implements RepresentationModelAssembler<Artist, EntityModel<Artist>> {
    @Override
    public EntityModel<Artist> toModel(Artist entity) {

        Link self = linkTo(methodOn(ArtistController.class).getSingleArtist(entity.getId())).withSelfRel();


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<String> authorities = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        if(authorities.contains("ROLE_ADMIN")){

                self = self        .andAffordance(afford(methodOn(ArtistController.class).deleteSingleArtist(entity.getId())))
                        .andAffordance(afford(methodOn(ArtistController.class).updateSingleArtist(entity.getId(), new ArtistDto())));

        }


            return EntityModel.of(entity).add(
            self,
                    linkTo(methodOn(ArtistController.class).getSongsByArtist(entity.getId(), null)).withRel("songs")
            );

    }
}
