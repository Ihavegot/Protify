package com.protify.Protify.controllers;

import com.protify.Protify.components.PlaylistModelAssembler;
import com.protify.Protify.components.UserModelAssembler;
import com.protify.Protify.models.Playlist;
import com.protify.Protify.models.User;
import com.protify.Protify.service.PlaylistService;
import com.protify.Protify.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.web.bind.annotation.*;

@RestController
@ExposesResourceFor(User.class)
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    @NonNull
    private  final UserService userService;

    @NonNull
private  final     PagedResourcesAssembler<User> pagedUserAssembler;

    @NonNull
    private  final     PagedResourcesAssembler<Playlist> pagedPlaylistAssembler;
    @NonNull
    private  final UserModelAssembler userModelAssembler; @NonNull
    private final PlaylistService playlistService;
    @NonNull
    private final PlaylistModelAssembler playlistModelAssembler;

    @GetMapping

    public PagedModel<EntityModel<User>> getUser(@ParameterObject Pageable pageable, @RequestParam(required = false) Integer page,
                                                 @RequestParam(required = false)         Integer size,
                                                 @RequestParam(required = false)        String[] sort){
        Page<User> users = userService.findAll(pageable);
        return pagedUserAssembler.toModel(users, userModelAssembler);
    }

    @GetMapping("{id}")
    public EntityModel<User> getSingleUser(@PathVariable("id") User user){
        return userModelAssembler.toModel(user);
    }


    @GetMapping("{id}/playlists")

    public PagedModel<EntityModel<Playlist>> getPlaylists(@PathVariable Long id, @ParameterObject Pageable pageable, @RequestParam(required = false) Integer page,
                                                          @RequestParam(required = false)         Integer size,
                                                          @RequestParam(required = false)        String[] sort){
        Page<Playlist> playlists = playlistService.findAllByUserId(id, pageable);
        return pagedPlaylistAssembler.toModel(playlists, playlistModelAssembler);
    }
}
