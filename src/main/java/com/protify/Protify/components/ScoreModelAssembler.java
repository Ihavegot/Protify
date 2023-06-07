package com.protify.Protify.components;

import com.protify.Protify.controllers.PlaylistController;
import com.protify.Protify.controllers.ScoreController;
import com.protify.Protify.controllers.SongsController;
import com.protify.Protify.dtos.SongDto;
import com.protify.Protify.models.*;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
@RequiredArgsConstructor
public class ScoreModelAssembler implements RepresentationModelAssembler<Score, EntityModel<Score>> {
    private final EntityLinks links;
    private final LinkRelationProvider linkRelationProvider;

    @Override
    public EntityModel<Score> toModel(Score entity) {
        HalModelBuilder builder;
        try {
            builder = HalModelBuilder.halModelOf(entity)
                    .link(links.linkToItemResource(entity, Score::getId)
                            .andAffordance(afford(methodOn(ScoreController.class).deleteSingleScore(entity.getId())))
                            .andAffordance(afford(methodOn(ScoreController.class).updateSingleScore(entity.getId(), null))
                            ));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return (EntityModel<Score>) builder.build();
    }
}
