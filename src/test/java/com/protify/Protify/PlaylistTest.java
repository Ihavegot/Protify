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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ProtifyApplication.class)
@AutoConfigureMockMvc

class PlaylistTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private PlaylistRepository playlistRepository;



    @BeforeEach
    public void beforeEach(){

        playlistRepository.deleteAll();

    }


    @Test
    public void all() throws Exception {
        var entities = playlistRepository.saveAll(IntStream.range(0, 50).mapToObj((i)->
                Playlist.builder().build()).toList());




        mvc.perform(get("/playlist")      .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.size").value(20))
                .andExpect(jsonPath("$.page.totalElements").value(50))
                .andExpect(jsonPath("$.page.totalPages").value(3))
                .andExpect(jsonPath("$.page.number").value(0))
                .andExpect(jsonPath("$._links.self.href").value("http://localhost/playlist?page=0&size=20"))
                .andExpect(jsonPath("$._links.first.href").value("http://localhost/playlist?page=0&size=20"))
                .andExpect(jsonPath("$._links.next.href").value("http://localhost/playlist?page=1&size=20"))
                .andExpect(jsonPath("$._links.last.href").value("http://localhost/playlist?page=2&size=20"))
                .andExpect(jsonPath("$._embedded.playlistList").isArray())
                .andExpect(jsonPath("$._embedded.playlistList", hasSize(20)))
                .andExpect(jsonPath("$._embedded.playlistList[3]._links.songs.href").value("http://localhost/playlist/"+entities.get(3).getId()+"/song"))
                .andExpect(jsonPath("$._embedded.playlistList[3]._links.self.href").value("http://localhost/playlist/"+entities.get(3).getId()))

        ;
    }


    @Test
    public void one() throws Exception {
        var entities = playlistRepository.saveAll(IntStream.range(0, 50).mapToObj((i)->
                Playlist.builder().build()).toList());



        mvc.perform(get("/playlist/"+entities.get(5).getId())      .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href").value("http://localhost/playlist/"+entities.get(5).getId()))
                .andExpect(jsonPath("$._links.songs.href").value("http://localhost/playlist/"+entities.get(5).getId()+"/song"))
                .andExpect(jsonPath("$._links.self.href").value("http://localhost/playlist/"+entities.get(5).getId()))

        ;
    }
}
