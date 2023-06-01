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
import java.util.List;
import java.util.stream.IntStream;

 
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ProtifyApplication.class)
@ExtendWith(SoftAssertionsExtension.class)

class PlaylistTest {
    @Value(value="${local.server.port}")
    private int port;
    private Traverson traverson;

    @BeforeEach
    public void beforeEach(){
        playlistRepository.deleteAll();

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
                .toObject(new ParameterizedTypeReference<PagedModel<EntityModel<Songs>>>(){});



        softly.assertThat(page.getMetadata().getSize()).isEqualTo(20);
        softly.assertThat(page.getMetadata().getTotalElements()).isEqualTo(50);
        softly.assertThat(page.getMetadata().getTotalPages()).isEqualTo(3);
        softly.assertThat(page.getMetadata().getNumber()).isEqualTo(1);
        softly.assertThat(page.getRequiredLink(IanaLinkRelations.SELF)).isEqualTo(Link.of("http://localhost:"+port+"/playlists?page=1&size=20&sort=id,asc").withRel(IanaLinkRelations.SELF)) ;
        softly.assertThat(page.getRequiredLink(IanaLinkRelations.FIRST)).isEqualTo(Link.of("http://localhost:"+port+"/playlists?page=0&size=20&sort=id,asc").withRel(IanaLinkRelations.FIRST)) ;
        softly.assertThat(page.getNextLink().orElse(null)).isEqualTo(Link.of("http://localhost:"+port+"/playlists?page=2&size=20&sort=id,asc").withRel(IanaLinkRelations.NEXT)) ;
        softly.assertThat(page.getPreviousLink().orElse(null)).isEqualTo(Link.of("http://localhost:"+port+"/playlists?page=0&size=20&sort=id,asc").withRel(IanaLinkRelations.PREV)) ;
        softly.assertThat(page.getContent()).hasSize(20);
        softly.assertThat( page.getContent().stream().toList().get(3).getContent().getId()).isEqualTo(entities.get(23).getId());


    }


    @Test
    public void one() throws Exception {
        var entities = playlistRepository.saveAll(IntStream.range(0, 50).mapToObj((i)->
                Playlist.builder().build()).toList());



//        mvc.perform(get("/playlists/"+entities.get(5).getId()).accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$._links.self.href").value("http://localhost/playlists/"+entities.get(5).getId()))
//                .andExpect(jsonPath("$.title").value(entities.get(5).getTitle()));
    }
}
