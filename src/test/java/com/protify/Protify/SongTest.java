package com.protify.Protify;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.protify.Protify.models.Artist;
import com.protify.Protify.models.Songs;
import com.protify.Protify.repository.SongRepository;
import org.aspectj.lang.annotation.After;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.*;
import org.springframework.hateoas.client.Hop;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.server.core.TypeReferences;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.assertj.core.api.SoftAssertions;
import org.springframework.core.ParameterizedTypeReference;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ProtifyApplication.class)
@ExtendWith(SoftAssertionsExtension.class)


class SongTest {

    @Value(value="${local.server.port}")
    private int port;
    private Traverson traverson;

    @BeforeEach
    public void beforeEach(){

        songRepository.deleteAll();

        traverson = new Traverson(URI.create("http://localhost:"+port+"/"), MediaTypes.HAL_JSON);

    }


 @Autowired
     private SongRepository songRepository;









    @InjectSoftAssertions
    private SoftAssertions softly;



 @Test
    public void all() throws Exception {
//     given
     var entities = songRepository.saveAll(IntStream.range(0, 25).mapToObj((i)->
             Songs.builder().artist(new Artist()).title("Title " +i).build()).toList());

     songRepository.saveAll(IntStream.range(25, 50).mapToObj((i)->
             Songs.builder().title("Title " +i).build()).toList());


//     when
     var response = traverson.follow(Hop.rel("songs")
             .withParameter("page", 1)
             .withParameter("sort", "id")).follow("last", "prev", "first", "next", "self")
             ;
//     then
     var page = response
             .toObject(new ParameterizedTypeReference<PagedModel<EntityModel<Songs>>>(){});



     softly.assertThat(page.getMetadata().getSize()).isEqualTo(20);
     softly.assertThat(page.getMetadata().getTotalElements()).isEqualTo(50);
     softly.assertThat(page.getMetadata().getTotalPages()).isEqualTo(3);
     softly.assertThat(page.getMetadata().getNumber()).isEqualTo(1);
     softly.assertThat(page.getContent()).hasSize(20);
     softly.assertThat( page.getContent().stream().toList().get(3).getContent().getId()).isEqualTo(entities.get(23).getId());


     //        and _embedded.artist

     Artist artist = new ObjectMapper().convertValue(response.toObject("$._embedded.songsList[3]._embedded.artist"), Artist.class);
     softly.assertThat(artist.getId()).isEqualTo(entities.get(23).getArtist().getId());
     softly.assertThat(artist.getArtistName()).isEqualTo(entities.get(23).getArtist().getArtistName());
     softly.assertThat(artist.getName()).isEqualTo(entities.get(23).getArtist().getName());
     softly.assertThat(artist.getSurname()).isEqualTo(entities.get(23).getArtist().getSurname());
 }


    @Test
    public void one() throws Exception {
//    given

        var entities = songRepository.saveAll(IntStream.range(0, 25).mapToObj((i)->
                Songs.builder().artist(new Artist()).title("Title " +i).build()).toList());

        songRepository.saveAll(IntStream.range(25, 50).mapToObj((i)->
                Songs.builder().title("Title " +i).build()).toList());

//    when
var        response = traverson.follow("songs", "$._embedded.songsList[5]._links.self.href", "self");
        //    then
        EntityModel<Songs> song =
                response.toObject(new ParameterizedTypeReference<EntityModel<Songs>>() {
                });


        softly.assertThat(song.getContent().getId()).isEqualTo(entities.get(5).getId());
        softly.assertThat(song.getContent().getTitle()).isEqualTo(entities.get(5).getTitle());


        //        and _embedded.artist

        Artist artist = new ObjectMapper().convertValue(response.toObject("$._embedded.artist"), Artist.class);
        softly.assertThat(artist.getId()).isEqualTo(entities.get(5).getArtist().getId());
        softly.assertThat(artist.getArtistName()).isEqualTo(entities.get(5).getArtist().getArtistName());
        softly.assertThat(artist.getName()).isEqualTo(entities.get(5).getArtist().getName());
        softly.assertThat(artist.getSurname()).isEqualTo(entities.get(5).getArtist().getSurname());

    }
}