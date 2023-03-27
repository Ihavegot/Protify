package com.protify.Protify;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.protify.Protify.models.Artist;
import com.protify.Protify.models.Song;
import com.protify.Protify.repository.ArtistRepository;
import com.protify.Protify.repository.SongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ProtifyApplication.class)
@AutoConfigureMockMvc

class SongTest {
 @Autowired
    private MockMvc mvc;

 @Autowired
     private SongRepository songRepository;

 @Autowired
 private ArtistRepository artistRepository;


 @BeforeEach
 public void beforeEach(){

     songRepository.deleteAll();
     artistRepository.deleteAll();
 }


 @Test
    public void all() throws Exception {
     var foo = artistRepository.save(Artist.builder().name("Foo").build());

     var entities = songRepository.saveAll(IntStream.range(0, 50).mapToObj((i)->
             Song.builder().title("Title " +i).artist(foo).build()).toList());





     mvc.perform(get("/songs")      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
             .andExpect(jsonPath("$.page.size").value(20))
             .andExpect(jsonPath("$.page.totalElements").value(50))
             .andExpect(jsonPath("$.page.totalPages").value(3))
             .andExpect(jsonPath("$.page.number").value(0))
             .andExpect(jsonPath("$._links.self.href").value("http://localhost/songs"))
             .andExpect(jsonPath("$._links.first.href").value("http://localhost/songs?page=0&size=20"))
             .andExpect(jsonPath("$._links.next.href").value("http://localhost/songs?page=1&size=20"))
             .andExpect(jsonPath("$._links.last.href").value("http://localhost/songs?page=2&size=20"))
             .andExpect(jsonPath("$._links.profile.href").value("http://localhost/profile/songs"))
             .andExpect(jsonPath("$._embedded.songs").isArray())
             .andExpect(jsonPath("$._embedded.songs", hasSize(20)))
             .andExpect(jsonPath("$._embedded.songs[3]._links.artist.href").value("http://localhost/songs/"+entities.get(3).getId()+"/artist{?projection}"))
             .andExpect(jsonPath("$._embedded.songs[3]._links.self.href").value("http://localhost/songs/"+entities.get(3).getId()))
             .andExpect(jsonPath("$._embedded.songs[3].title").value(entities.get(3).getTitle()))
             .andExpect(jsonPath("$._embedded.songs[3]._embedded.artist.name").value(foo.getName()))
             .andExpect(jsonPath("$._embedded.songs[3]._embedded.artist._links.songs.href").value("http://localhost/artists/"+foo.getId()+"/songs"))
             .andExpect(jsonPath("$._embedded.songs[3]._embedded.artist._links.self.href").value("http://localhost/artists/"+foo.getId()+"{?projection}"))
             .andExpect(jsonPath("$._embedded.songs[3]._embedded.artist._links.self.templated").value(true))
;
 }


    @Test
    public void one() throws Exception {
        var foo = artistRepository.save(Artist.builder().name("Foo").build());

        var entities = songRepository.saveAll(IntStream.range(0, 50).mapToObj((i)->
                Song.builder().title("Title " +i).artist(foo).build()).toList());



        mvc.perform(get("/songs/"+entities.get(5).getId())      .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href").value("http://localhost/songs/"+entities.get(5).getId()))
                .andExpect(jsonPath("$._links.artist.href").value("http://localhost/songs/"+entities.get(5).getId()+"/artist{?projection}"))
                .andExpect(jsonPath("$._links.self.href").value("http://localhost/songs/"+entities.get(5).getId()))
                .andExpect(jsonPath("$.title").value(entities.get(5).getTitle()))
                .andExpect(jsonPath("$._embedded.artist.name").value(foo.getName()))
                .andExpect(jsonPath("$._embedded.artist._links.songs.href").value("http://localhost/artists/"+foo.getId()+"/songs"))
                .andExpect(jsonPath("$._embedded.artist._links.self.href").value("http://localhost/artists/"+foo.getId()+"{?projection}"))
                .andExpect(jsonPath("$._embedded.artist._links.self.templated").value(true))
        ;
    }
}