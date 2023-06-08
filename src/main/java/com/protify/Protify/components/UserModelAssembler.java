package com.protify.Protify.components;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.protify.Protify.controllers.UserController;
import com.protify.Protify.models.Playlist;
import com.protify.Protify.models.Songs;
import com.protify.Protify.models.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Affordance;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mediatype.hal.HalModelBuilder;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.LinkRelationProvider;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {
    @NonNull
    private final EntityLinks links;
    @NonNull
    private final LinkRelationProvider linkRelationProvider;

    @Override
    public EntityModel<User> toModel(User entity) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<String> authorities = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        Link self = links.linkToItemResource(entity, User::getId);

        if(auth.getName().equals(entity.getLogin()) && authorities.contains("SCOPE_profile")){
            self = self.andAffordance(afford(methodOn(UserController.class).deleteUser(entity.getId(), null)))
                    .andAffordance(afford(methodOn(UserController.class).patchUser(entity, null, null)))
                    .andAffordance(afford(methodOn(UserController.class).putUser(entity.getId(), null, null)));
        }

        HalModelBuilder builder = HalModelBuilder.halModelOf(entity)
                .link(self)
                .link(linkTo(methodOn(UserController.class).getPlaylists(entity.getId(), null, null, null, null)).withRel(
                        linkRelationProvider.getCollectionResourceRelFor(Playlist.class)
                ));

        return (EntityModel<User>) builder
                .build();
    }
}
