package com.protify.Protify;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.protify.Protify.models.Artist;
import com.protify.Protify.models.Playlist;
import com.protify.Protify.models.Songs;
import com.protify.Protify.repository.PlaylistRepository;
import com.protify.Protify.repository.SongRepository;
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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ProtifyApplication.class)
@ExtendWith(SoftAssertionsExtension.class)

class PlaylistTest {
    @Value(value="${local.server.port}")
    private int port;
    private Traverson traverson;

    @BeforeEach
    public void beforeEach(){
        playlistRepository.deleteAll();
songsRepository.deleteAll();
        traverson = new Traverson(URI.create("http://localhost:"+port+"/"), MediaTypes.HAL_JSON);

    }


    @Autowired
    private PlaylistRepository playlistRepository;









    @InjectSoftAssertions
    private SoftAssertions softly;



    @Test
    public void all() throws Exception {
        var entities = playlistRepository.saveAll(IntStream.range(0, 50).mapToObj((i)->
                Playlist.builder().build()).toList());



        var page = traverson.follow(Hop.rel("playlists")
                        .withParameter("page", 1)
                        .withParameter("sort", "id"))
                .follow("last", "prev", "first", "next", "self")
                .toObject(new ParameterizedTypeReference<PagedModel<EntityModel<Songs>>>(){});



        softly.assertThat(page.getMetadata().getSize()).isEqualTo(20);
        softly.assertThat(page.getMetadata().getTotalElements()).isEqualTo(50);
        softly.assertThat(page.getMetadata().getTotalPages()).isEqualTo(3);
        softly.assertThat(page.getMetadata().getNumber()).isEqualTo(1);
        softly.assertThat(page.getContent()).hasSize(20);
        softly.assertThat( page.getContent().stream().toList().get(3).getContent().getId()).isEqualTo(entities.get(23).getId());


    }


    @Test
    public void one() throws Exception {
        var entities = playlistRepository.saveAll(IntStream.range(0, 50).mapToObj((i)->
                Playlist.builder().build()).toList());

        EntityModel<Playlist> entity = traverson.follow("playlists", "$._embedded.playlists[5]._links.self.href", "self")
                .toObject(new ParameterizedTypeReference<>() {
                });

        softly.assertThat(entity.getContent().getId()).isEqualTo(entities.get(5).getId());
    }

    @Autowired
    private SongRepository songsRepository;


    @Test
    public void songs() throws Exception {
        var songs = songsRepository.saveAll(
                Stream.generate(Songs::new).limit(50).toList()
        );

        var entities = playlistRepository.saveAll(IntStream.range(0, 50).mapToObj((i)->
                Playlist.builder().songs(new HashSet<>(songs.subList(0, i))).build()).toList());

        PagedModel<EntityModel<Songs>> page = traverson.follow("playlists", "next", "$._embedded.playlists[10]._links.self.href")
                .follow(Hop.rel("songs").withParameter("page", 1).withParameter("sort", "id"))
                .follow("first", "next", "prev", "last",  "self")
                .toObject(new ParameterizedTypeReference<>() {
                });

        softly.assertThat(page.getMetadata().getSize()).isEqualTo(20);
        softly.assertThat(page.getMetadata().getTotalElements()).isEqualTo(30);
        softly.assertThat(page.getMetadata().getTotalPages()).isEqualTo(2);
        softly.assertThat(page.getMetadata().getNumber()).isEqualTo(1);
        softly.assertThat(page.getContent()).hasSize(10);
        softly.assertThat( page.getContent().stream().toList().get(3).getContent().getId()).isEqualTo(songs.get(23).getId());


    }
}
