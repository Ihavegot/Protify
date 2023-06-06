package com.protify.Protify;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.protify.Protify.models.Artist;
import com.protify.Protify.models.Playlist;
import com.protify.Protify.models.Songs;
import com.protify.Protify.models.User;
import com.protify.Protify.repository.PlaylistRepository;
import com.protify.Protify.repository.SongRepository;
import com.protify.Protify.repository.UserRepository;
import com.protify.Protify.service.UserService;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.assertj.core.util.Streams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.*;
import org.springframework.hateoas.client.Hop;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ProtifyApplication.class)
@ExtendWith(SoftAssertionsExtension.class)
class PlaylistTest {
    @Value(value = "${local.server.port}")
    private int port;
    private Traverson traverson;
    @Autowired
    private UserService userService;

    @BeforeEach
    public void beforeEach() {
        playlistRepository.deleteAll();
        userService.deleteAll();
        songsRepository.deleteAll();
        traverson = new Traverson(URI.create("http://localhost:" + port + "/"), MediaTypes.HAL_JSON);

    }


    @Autowired
    private PlaylistRepository playlistRepository;


    @InjectSoftAssertions
    private SoftAssertions softly;


    @Test
    public void all() throws Exception {
//given
        var entities = playlistRepository.saveAll(IntStream.range(0, 50).mapToObj((i) ->
                Playlist.builder().user(
                        userService.save(User.builder().login("login-" + i).password("password-" + i).email("email-" + i).build())
                ).build()).toList());

//when
        var response = traverson.follow(Hop.rel("playlists")
                        .withParameter("page", 1)
                        .withParameter("sort", "id"))
                .follow("last", "prev", "first", "next", "self");

//then

        var page = response
                .toObject(new ParameterizedTypeReference<PagedModel<EntityModel<Playlist>>>() {
                });


        softly.assertThat(page.getMetadata().getSize()).isEqualTo(20);
        softly.assertThat(page.getMetadata().getTotalElements()).isEqualTo(50);
        softly.assertThat(page.getMetadata().getTotalPages()).isEqualTo(3);
        softly.assertThat(page.getMetadata().getNumber()).isEqualTo(1);
        softly.assertThat(page.getContent()).hasSize(20);
        softly.assertThat(page.getContent().stream().toList().get(3).getContent().getId()).isEqualTo(entities.get(23).getId());

        //        and _embedded.user

        User author = new ObjectMapper().convertValue(response.toObject("$._embedded.playlists[3]._embedded.user"), User.class);
        softly.assertThat(author.getId()).isEqualTo(entities.get(23).getUser().getId());
        softly.assertThat(author.getPassword()).isNull();
        softly.assertThat(author.getEmail()).isEqualTo(entities.get(23).getUser().getEmail());
        softly.assertThat(author.getLogin()).isEqualTo(entities.get(23).getUser().getLogin());
    }


    @Test
    public void one() throws Exception {
//        given

        var entities = playlistRepository.saveAll(IntStream.range(0, 50).mapToObj((i) ->
                Playlist.builder().user(userService.save(User.builder().email("email-" + i).login("login-" + i).password("password-" + i).build())).build()).toList());

        //        when
        Traverson.TraversalBuilder response = traverson.follow("playlists", "$._embedded.playlists[5]._links.self.href", "self");
//        then

        EntityModel<Playlist> entity =
                response
                        .toObject(new ParameterizedTypeReference<>() {
                        });

        softly.assertThat(entity.getContent().getId()).isEqualTo(entities.get(5).getId());

        //        and _embedded.user

        User author = new ObjectMapper().convertValue(response.toObject("$._embedded.user"), User.class);

        softly.assertThat(author.getId()).isEqualTo(entities.get(5).getUser().getId());
        softly.assertThat(author.getPassword()).isNull();
        softly.assertThat(author.getEmail()).isEqualTo(entities.get(5).getUser().getEmail());
        softly.assertThat(author.getLogin()).isEqualTo(entities.get(5).getUser().getLogin());
    }

    @Autowired
    private SongRepository songsRepository;
    @Autowired
    private UserRepository userRepository;


    @Test
    public void songs() throws Exception {
//        given
        var songs = songsRepository.saveAll(
                Stream.generate(() -> Songs.builder().artist(null).build()).limit(25).toList()
        );

        songs.addAll(
                songsRepository.saveAll(Stream.generate(Songs::new).limit(25).toList()

                )
        );

        playlistRepository.saveAll(IntStream.range(0, 50).mapToObj((i) ->
                Playlist.builder().songs(new HashSet<>(songs.subList(0, i))).build()).toList());

        //        when
        Traverson.TraversalBuilder response = traverson.follow("playlists", "next", "$._embedded.playlists[10]._links.self.href")
                .follow(Hop.rel("songs").withParameter("page", 1).withParameter("sort", "id"))
                .follow("first", "next", "prev", "last", "self");
//        then
        PagedModel<EntityModel<Songs>> page = response
                .toObject(new ParameterizedTypeReference<>() {
                });

        softly.assertThat(page.getMetadata().getSize()).isEqualTo(20);
        softly.assertThat(page.getMetadata().getTotalElements()).isEqualTo(30);
        softly.assertThat(page.getMetadata().getTotalPages()).isEqualTo(2);
        softly.assertThat(page.getMetadata().getNumber()).isEqualTo(1);
        softly.assertThat(page.getContent()).hasSize(10);

        softly.assertThat(page.getContent().stream().toList().get(3).getContent().getId()).isEqualTo(songs.get(23).getId());
        softly.assertThat(page.getContent().stream().toList().get(3).getContent().getTitle()).isEqualTo(songs.get(23).getTitle());
        //        and _embedded.artist

//        Artist artist = new ObjectMapper().convertValue(response.toObject("$._embedded.songs[3]._embedded.artist"), Artist.class);
//        softly.assertThat(artist.getId()).isEqualTo(songs.get(23).getArtist().getId());
//        softly.assertThat(artist.getArtistName()).isEqualTo(songs.get(23).getArtist().getArtistName());
//        softly.assertThat(artist.getName()).isEqualTo(songs.get(23).getArtist().getName());
//        softly.assertThat(artist.getSurname()).isEqualTo(songs.get(23).getArtist().getSurname());
    }
}
