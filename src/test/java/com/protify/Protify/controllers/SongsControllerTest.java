package com.protify.Protify.controllers;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.protify.Protify.ProtifyApplication;
import com.protify.Protify.adapter.FormsTraverson;
import com.protify.Protify.dtos.ScoredSongDto;
import com.protify.Protify.dtos.SongScoreDto;
import com.protify.Protify.embeddable.ScoreKey;
import com.protify.Protify.models.Score;
import com.protify.Protify.models.Songs;
import com.protify.Protify.models.User;
import com.protify.Protify.repository.ScoreRepository;
import com.protify.Protify.service.SongService;
import com.protify.Protify.service.UserService;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ProtifyApplication.class)
    @ExtendWith(SoftAssertionsExtension.class)
    @AutoConfigureMockMvc
    class SongsControllerTest {





        private FormsTraverson traverson;



        @Autowired
        private MockMvc mvc;


        @Autowired
        private UserService userService;
        @Autowired
        private SongService songService;
    @Autowired
    private UserService songsRepository;



    @BeforeEach
        public void beforeEach(){



            userService.deleteAll();

            songsRepository.deleteAll();

            traverson = new FormsTraverson(mvc


            );

        }

        @InjectSoftAssertions
        private SoftAssertions softly;
    @Autowired
    private ScoreRepository scoreRepository;

    @Test

    @WithMockUser(username="foo")


    void updateSongsScore() throws Exception {
        var user = userService.save(User.builder().login("foo").build());
;
        Songs song = songService.save(Songs.builder().build());

        scoreRepository.save(
                Score.builder().id(ScoreKey.builder().userId(user.getId()).songId(song.getId()).build()).score(0f).user(user).songs(     song ).build()
        );

        FormsTraverson.Builder request = traverson.follow("songs", "$._embedded.songs[0]._links.self.href");



        softly.assertThat(request.<String>toObject("$._templates.default.method")).isEqualTo("PUT");


        EntityModel<ScoredSongDto> entity = request.put("$._templates.default.target", SongScoreDto.builder().score(12f).build()).toObject(new TypeReference<EntityModel<ScoredSongDto>>() {
        });

        softly.assertThat(entity.getContent().getScore()).isEqualTo(12f);

    }


}