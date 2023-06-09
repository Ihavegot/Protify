package com.protify.Protify.components;

import com.protify.Protify.controllers.PlaylistController;
import com.protify.Protify.dtos.PlaylistDto;
import com.protify.Protify.dtos.PlaylistTitleDto;
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
import org.springframework.hateoas.server.TypedEntityLinks;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.swing.text.html.parser.Entity;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class PlaylistModelAssembler implements RepresentationModelAssembler<Playlist, EntityModel<Playlist>> {
    private final EntityLinks links;
    private final LinkRelationProvider linkRelationProvider;
    @Override
    public EntityModel<Playlist> toModel(Playlist entity) {

        Link self = links.linkToItemResource(entity, Playlist::getId);


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<String> authorities = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();


        if( authorities.contains("ROLE_ADMIN") || (entity.getUser()!=null && auth.getName().equals(entity.getUser().getLogin()))) {
         self = self
                 .andAffordance(afford(methodOn(PlaylistController.class).deleteSinglePlaylist(entity.getId())))
                 .andAffordance(afford(methodOn(PlaylistController.class).updateSinglePlaylist(entity.getId(), new PlaylistTitleDto())));
        }

        HalModelBuilder builder = HalModelBuilder.halModelOf(entity).link(self)

                .link(linkTo(methodOn(PlaylistController.class).getPlaylistSongs(entity.getId(), null, null, null, null)).withRel(
                        linkRelationProvider.getCollectionResourceRelFor(Songs.class)
                ))
                ;


        if (entity.getUser() != null) {
            builder = builder
                    .preview(entity.getUser())
                    .forLink(links.linkToItemResource(entity.getUser(), User::getId).withRel(linkRelationProvider.getItemResourceRelFor(User.class)))
            ;
        }
        return (EntityModel<Playlist>) builder
                .build();
    }
}
