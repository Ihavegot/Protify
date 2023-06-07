package com.protify.Protify.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.protify.Protify.components.PlaylistModelAssembler;
import com.protify.Protify.components.UserModelAssembler;
import com.protify.Protify.dtos.UserDto;
import com.protify.Protify.mappers.UserMapper;
import com.protify.Protify.models.Playlist;
import com.protify.Protify.models.User;
import com.protify.Protify.repository.UserRepository;
import com.protify.Protify.service.PlaylistService;
import com.protify.Protify.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
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
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ExposesResourceFor(User.class)
@RequestMapping(value = "/users", produces = {MediaTypes.HAL_JSON_VALUE, MediaTypes.HAL_FORMS_JSON_VALUE})
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

    private final UserRepository userRepository;


    @GetMapping
    @Operation(summary="User list")
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
    @Operation(summary="Create User")
    public ResponseEntity<EntityModel<User>> postUser(@RequestBody @Valid UserDto data, @RequestHeader(required = false)  @Parameter(hidden = true) String Accept) {
        User user = userService.save(Mappers.getMapper(UserMapper.class).create(data));

        return ResponseEntity.created(
                linkTo(methodOn(UserController.class).getSingleUser(user.getId())).toUri()
        ).body(Accept != null ? userModelAssembler.toModel(user) : null);
    }


    @GetMapping("{id}")
    @Operation(summary="Get User")
    public EntityModel<User> getSingleUser(@PathVariable("id") Long id) {
        User user = userService.getSingle(id);
        return userModelAssembler.toModel(user);
    }


    @GetMapping("{id}/playlists")
    @Operation(summary="Get User's Playlist list")
    public PagedModel<EntityModel<Playlist>> getPlaylists(@PathVariable Long id, @ParameterObject Pageable pageable, @RequestParam(required = false) Integer page,
                                                          @RequestParam(required = false) Integer size,
                                                          @RequestParam(required = false) String[] sort) {
        Page<Playlist> playlists = playlistService.findAllByUserId(id, pageable);
        return pagedPlaylistAssembler.toModel(playlists, playlistModelAssembler);
    }

    @DeleteMapping("{id}")
    @Operation(summary="Delete User")
    public ResponseEntity<EntityModel<User>> deleteUser(@PathVariable("id") Long id, @RequestHeader(required = false) @Parameter(hidden = true) String Accept) {
        var user = userService.getSingle(id);
        userService.delete(user);

        if (Accept == null) {
            return ResponseEntity.noContent().build();
        }


        return ResponseEntity.ok(userModelAssembler.toModel(user));
    }

    @PutMapping("{id}")
    @Operation(summary="Update User")
    public ResponseEntity<EntityModel<User>> putUser(@PathVariable Long id, @RequestBody @Valid UserDto body, @RequestHeader(required = false) @Parameter(hidden = true) String Accept) {

        User user = Mappers.getMapper(UserMapper.class).create(body);
        user.setId(id);
        user = userService.save(user);

        if (Accept == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(userModelAssembler.toModel(user));
    }

    @PatchMapping("{id}")
    @Operation(summary="Update User")
    public ResponseEntity<EntityModel<User>> patchUser(@PathVariable("id") @Parameter(schema = @Schema(type="integer")) User user, @Valid @RequestBody UserDto body, @RequestHeader(required = false) @Parameter(hidden=true) String Accept) {
        Mappers.getMapper(UserMapper.class).update(user, body);

        user = userService.save(user);

        if (Accept == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(userModelAssembler.toModel(user));
    }
}
