package com.protify.Protify.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.protify.Protify.components.PlaylistModelAssembler;
import com.protify.Protify.components.UserModelAssembler;
import com.protify.Protify.dtos.UserDto;
import com.protify.Protify.mappers.UserMapper;
import com.protify.Protify.models.Playlist;
import com.protify.Protify.models.User;
import com.protify.Protify.service.PlaylistService;
import com.protify.Protify.service.UserService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ExposesResourceFor(User.class)
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    @NonNull
    private final UserService userService;

    @NonNull
    private final PagedResourcesAssembler<User> pagedUserAssembler;

    @NonNull
    private final PagedResourcesAssembler<Playlist> pagedPlaylistAssembler;
    @NonNull
    private final UserModelAssembler userModelAssembler;
    @NonNull
    private final PlaylistService playlistService;
    @NonNull
    private final PlaylistModelAssembler playlistModelAssembler;


    @GetMapping
    public PagedModel<EntityModel<User>> getUser(@ParameterObject Pageable pageable, @RequestParam(required = false) Integer page,
                                                 @RequestParam(required = false) Integer size,
                                                 @RequestParam(required = false) String[] sort) {
        Page<User> users = userService.findAll(pageable);
        PagedModel<EntityModel<User>> model = pagedUserAssembler.toModel(users, userModelAssembler);

        model.mapLink(IanaLinkRelations.SELF, link ->
                link.andAffordance(afford(methodOn(UserController.class).postUser(null, null)))
        );

        return model;
    }

    @PostMapping
    public ResponseEntity<EntityModel<User>> postUser(@RequestBody @Valid UserDto data, @RequestHeader(required = false) String Accept) {
        User user = userService.save(Mappers.getMapper(UserMapper.class).create(data));

        return ResponseEntity.created(
                linkTo(methodOn(UserController.class).getSingleUser(user)).toUri()
        ).body(Accept != null ? userModelAssembler.toModel(user) : null);
    }


    @GetMapping("{id}")
    public EntityModel<User> getSingleUser(@PathVariable("id") User user) {
        return userModelAssembler.toModel(user);
    }


    @GetMapping("{id}/playlists")
    public PagedModel<EntityModel<Playlist>> getPlaylists(@PathVariable Long id, @ParameterObject Pageable pageable, @RequestParam(required = false) Integer page,
                                                          @RequestParam(required = false) Integer size,
                                                          @RequestParam(required = false) String[] sort) {
        Page<Playlist> playlists = playlistService.findAllByUserId(id, pageable);
        return pagedPlaylistAssembler.toModel(playlists, playlistModelAssembler);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<EntityModel<User>> deleteUser(@PathVariable("id") User user, @RequestHeader(required = false) String Accept) {
        userService.delete(user);

        if (Accept == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(userModelAssembler.toModel(user));
    }

    @PutMapping("{id}")
    public ResponseEntity<EntityModel<User>> putUser(@PathVariable Long id, @RequestBody @Valid UserDto body, @RequestHeader(required = false) String Accept) {

        User user = Mappers.getMapper(UserMapper.class).create(body);
        user.setId(id);
        user = userService.save(user);

        if (Accept == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(userModelAssembler.toModel(user));
    }

    @PatchMapping("{id}")
    public ResponseEntity<EntityModel<User>> patchUser(@PathVariable("id") User user, @Valid @RequestBody UserDto body, @RequestHeader(required = false) String Accept) {


        Mappers.getMapper(UserMapper.class).update(user, body);

        user = userService.save(user);

        if (Accept == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(userModelAssembler.toModel(user));
    }
}
