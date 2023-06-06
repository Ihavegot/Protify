package com.protify.Protify.components;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.protify.Protify.controllers.UserController;
import com.protify.Protify.models.Playlist;
import com.protify.Protify.models.Songs;
import com.protify.Protify.models.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.mediatype.hal.HalModelBuilder;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.LinkRelationProvider;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {
    @NonNull
    private final EntityLinks links;
    @NonNull
    private final LinkRelationProvider linkRelationProvider;

    @Override
    public EntityModel<User> toModel(User entity) {
        HalModelBuilder builder = HalModelBuilder.halModelOf(entity)
                .link(links.linkToItemResource(entity, User::getId)
                        .andAffordance(afford(methodOn(UserController.class).deleteUser(entity, null)))
                        .andAffordance(afford(methodOn(UserController.class).patchUser(entity, null, null)))
                        .andAffordance(afford(methodOn(UserController.class).putUser(entity.getId(), null, null)))
                )
                .link(linkTo(methodOn(UserController.class).getPlaylists(entity.getId(), null, null, null, null)).withRel(
                        linkRelationProvider.getCollectionResourceRelFor(Playlist.class)
                ));
        return (EntityModel<User>) builder
                .build();
    }
}
