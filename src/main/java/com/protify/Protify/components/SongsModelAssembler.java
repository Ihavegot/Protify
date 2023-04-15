package com.protify.Protify.components;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.protify.Protify.SongsModel;
import com.protify.Protify.controllers.SongsController;
import com.protify.Protify.models.Songs;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class SongsModelAssembler extends RepresentationModelAssemblerSupport<Songs, SongsModel> {
    public SongsModelAssembler() {
        super(SongsController.class, SongsModel.class);
    }

    @Override
    public SongsModel toModel(Songs entity) {
        SongsModel model = new SongsModel();
        BeanUtils.copyProperties(entity, model);
        return model;
    }
}
