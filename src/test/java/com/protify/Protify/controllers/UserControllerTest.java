package com.protify.Protify.controllers;

import static org.junit.jupiter.api.Assertions.*;

import com.protify.Protify.ModelValidators;
import com.protify.Protify.ProtifyApplication;
import com.protify.Protify.models.Artist;
import com.protify.Protify.models.Playlist;
import com.protify.Protify.models.Songs;
import com.protify.Protify.models.User;
import com.protify.Protify.repository.PlaylistRepository;
import com.protify.Protify.service.UserService;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.client.Hop;
import org.springframework.hateoas.client.Traverson;

import java.net.URI;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ProtifyApplication.class)
@ExtendWith(SoftAssertionsExtension.class)


class UserControllerTest {

    @Value(value="${local.server.port}")
    private int port;
    private Traverson traverson;


    @Autowired
    private UserService userService;

    @BeforeEach
    public void beforeEach(){

        userService.deleteAll();

        traverson = new Traverson(URI.create("http://localhost:"+port+"/"), MediaTypes.HAL_JSON);

    }

    @Test
    void getUsers() {
        //    given

        var users =     userService.saveAll(Stream.generate(User::new).limit(50).toList());

        //when
        var response = traverson.follow(Hop.rel("users")
                        .withParameter("page", 1)
                        .withParameter("sort", "id"))
                .follow("last", "prev", "first", "next", "self");
        //     then
        var page = response
                .toObject(new ParameterizedTypeReference<PagedModel<EntityModel<User>>>(){});



        softly.assertThat(page.getMetadata().getSize()).isEqualTo(20);
        softly.assertThat(page.getMetadata().getTotalElements()).isEqualTo(50);
        softly.assertThat(page.getMetadata().getTotalPages()).isEqualTo(3);
        softly.assertThat(page.getMetadata().getNumber()).isEqualTo(1);
        softly.assertThat(page.getContent()).hasSize(20);
        ModelValidators.validateUser(softly, page.getContent().stream().toList().get(3).getContent(), users.get(23));

    }

    @Test
    void getSingleUser() {

        //    given

        var user =     userService.save(new User());



//    when
        var        response = traverson.follow("users", "$._embedded.users[0]._links.self.href", "self");
        //    then
        EntityModel<User> entity =
                response.toObject(new ParameterizedTypeReference<>() {
                });


     ModelValidators.validateUser(softly, entity.getContent(), user);

    }



    @InjectSoftAssertions
    private SoftAssertions softly;
    @Autowired
    private PlaylistRepository playlistRepository;

    @Test
    void getPlaylists() {
        //    given
        var user =     userService.save(new User());
        List<Playlist> playlists = playlistRepository.saveAll(Stream.generate(()->Playlist.builder().user(user).build()).limit(50).toList());

        playlistRepository.saveAll(Stream.generate(Playlist::new).limit(50).toList());





//    when
        var        response = traverson.follow("users", "$._embedded.users[0]._links.self.href")
                .follow(Hop.rel("playlists")                        .withParameter("page", 1)
                        .withParameter("sort", "id"))
                .follow("last", "prev", "first", "next", "self");

        //    then
        PagedModel<EntityModel<Playlist>> page =
                response.toObject(new ParameterizedTypeReference<>() {
                });


        softly.assertThat(page.getMetadata().getSize()).isEqualTo(20);
        softly.assertThat(page.getMetadata().getTotalElements()).isEqualTo(50);
        softly.assertThat(page.getMetadata().getTotalPages()).isEqualTo(3);
        softly.assertThat(page.getMetadata().getNumber()).isEqualTo(1);
        softly.assertThat(page.getContent()).hasSize(20);
        ModelValidators.validatePlaylist(softly, page.getContent().stream().toList().get(3).getContent(), playlists.get(23));
    }
}