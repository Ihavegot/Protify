package com.protify.Protify.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.PathNotFoundException;
import com.protify.Protify.ModelValidators;
import com.protify.Protify.ProtifyApplication;
import com.protify.Protify.adapter.FormsTraverson;

import com.protify.Protify.dtos.UserDto;
import com.protify.Protify.models.Playlist;
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.Pageable;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Stream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ProtifyApplication.class)
@ExtendWith(SoftAssertionsExtension.class)
@AutoConfigureMockMvc
class UserControllerTest {

    

    

    private FormsTraverson traverson;



    @Autowired
    private MockMvc mvc;


    @Autowired
    private UserService userService;




    @BeforeEach
    public void beforeEach(){
        playlistRepository.deleteAll();
        userService.deleteAll();
        traverson = new FormsTraverson(mvc


        );

    }


    @Test
    void getUsers() throws Exception {
        //    given

        var users =     userService.saveAll(Stream.generate(User::new).limit(50).toList());

        //when
        var response = traverson.follow(FormsTraverson.Hop.rel("users")
                        .withParameter("page", 1)
                        .withParameter("sort", "id"))
                .follow("last", "prev", "first", "next", "self");
        //     then
        var page = response
                .toObject(new TypeReference<PagedModel<EntityModel<User>>>(){});



        softly.assertThat(page.getMetadata().getSize()).isEqualTo(20);
        softly.assertThat(page.getMetadata().getTotalElements()).isEqualTo(50);
        softly.assertThat(page.getMetadata().getTotalPages()).isEqualTo(3);
        softly.assertThat(page.getMetadata().getNumber()).isEqualTo(1);
        softly.assertThat(page.getContent()).hasSize(20);
        ModelValidators.validateUser(softly, page.getContent().stream().toList().get(3).getContent(), users.get(23));

    }

    @Test
    void getSingleUser() throws Exception {

        //    given

        var user =     userService.save(new User());



//    when
        var        response = traverson.follow("users", "$._embedded.users[0]._links.self.href", "self");
        //    then
        EntityModel<User> entity =
                response.toObject(new TypeReference<>() {
                });


     ModelValidators.validateUser(softly, entity.getContent(), user);

    }



    @InjectSoftAssertions
    private SoftAssertions softly;
    @Autowired
    private PlaylistRepository playlistRepository;

    @Test
    void getPlaylists() throws Exception {
        //    given
        var user =     userService.save(new User());
        List<Playlist> playlists = playlistRepository.saveAll(Stream.generate(()->Playlist.builder().user(user).build()).limit(50).toList());

        playlistRepository.saveAll(Stream.generate(Playlist::new).limit(50).toList());





//    when
        var        response = traverson.follow("users", "$._embedded.users[0]._links.self.href")
                .follow(FormsTraverson.Hop.rel("playlists")                        .withParameter("page", 1)
                        .withParameter("sort", "id"))
                .follow("last", "prev", "first", "next", "self");
        //    then


        PagedModel<EntityModel<Playlist>> page =
                response.toObject(new TypeReference<>() {
                });


        softly.assertThat(page.getMetadata().getSize()).isEqualTo(20);
        softly.assertThat(page.getMetadata().getTotalElements()).isEqualTo(50);
        softly.assertThat(page.getMetadata().getTotalPages()).isEqualTo(3);
        softly.assertThat(page.getMetadata().getNumber()).isEqualTo(1);
        softly.assertThat(page.getContent()).hasSize(20);
        ModelValidators.validatePlaylist(softly, page.getContent().stream().toList().get(3).getContent(), playlists.get(23));
    }
    


    @Autowired
    private ObjectMapper mapper;

    @Test
    void postUser() throws Exception {





        UserDto expected = new UserDto();
        expected.setLogin("user");
        expected.setPassword("password");
        expected.setEmail("user@example.com");


        //when
        var request = traverson.follow("users");
        //then
        softly.assertThat(request.<String>toObject("$._templates.default.method")).isEqualTo("POST");

        EntityModel<User> result = request.post("$._templates.default.target"
                ,expected).toObject( new TypeReference<>() {
        });


        User other = userService.findAll(Pageable.unpaged()).stream().findFirst().orElseThrow();
        softly.assertThat(other.getEmail()).isEqualTo(expected.getEmail());
        softly.assertThat(other.getLogin()).isEqualTo(expected.getLogin());
        softly.assertThat(other.getPassword()).isEqualTo(expected.getPassword());

        ModelValidators.validateUser(softly, result.getContent(), other);

    }




    @Test
    @WithMockUser(username="foo")
    void deleteUser() throws Exception {
         var user = userService.save(User.builder().login("foo").build());


        //when
        var request = traverson.follow("users", "$._embedded.users[0]._links.self.href");
        //then
        softly.assertThat(request.<String>toObject("$._templates.default.method")).isEqualTo("DELETE");

        EntityModel<User> result = request
                .delete("self").toObject(new TypeReference<>() {
        });




        softly.assertThat(userService.findAll(Pageable.unpaged())).hasSize(0);

            ModelValidators.validateUser(softly, result.getContent(),user);

    }


    @Test
    @WithMockUser(username="foo")
    void deleteDifferentUser() throws Exception {
        userService.save(User.builder().login("bar").build());
        var user = userService.save(User.builder().login("foo").build());


        //when
        var request = traverson.follow("users", "$._embedded.users[0]._links.self.href");
        //then
        softly.assertThatThrownBy(()-> request.<String>toObject("$._templates.default.method")).isInstanceOf(PathNotFoundException.class);


      request
                .delete("self").toResult().andExpect(status().isForbidden());


    }

    @Test
    @WithMockUser(username="before")
    void putUser_update() throws Exception {
//        given

        userService.save(User.builder().email("before").login("before").password("before").build());

        UserDto expected = new UserDto();
        expected.setLogin("user");
        expected.setPassword("password");
        expected.setEmail("user@example.com");


//when
var request =  traverson.follow("users","$._embedded.users[0]._links.self.href");
//then


        softly.assertThat(request.<String>toObject("$._templates.putUser.method")).isEqualTo("PUT");

        EntityModel<User> result = request
                .put("self",expected).toObject( new TypeReference<>() {
                })
                ;


        User other = userService.findAll(Pageable.unpaged()).stream().findFirst().orElseThrow();
        softly.assertThat(other.getEmail()).isEqualTo(expected.getEmail());
        softly.assertThat(other.getLogin()).isEqualTo(expected.getLogin());
        softly.assertThat(other.getPassword()).isEqualTo(expected.getPassword());

        ModelValidators.validateUser(softly, result.getContent(), other);
    }





    @Test
    @WithMockUser(username="before")
    void patchUser() throws Exception {
        //        given

       User saved = userService.save(User.builder().email("before").login("before").password("before").build());

        UserDto expected = new UserDto();
        expected.setEmail("user@example.com");


        //        when
        var request = traverson.follow("users", "$._embedded.users[0]._links.self.href");

        //        then

        softly.assertThat(request.<String>toObject("$._templates.patchUser.method")).isEqualTo("PATCH");


         EntityModel<User> result = request
                .patch("self",expected).toObject(new TypeReference<>() {
                })
        ;


        User other = userService.findAll(Pageable.unpaged()).stream().findFirst().orElseThrow();
        softly.assertThat(other.getEmail()).isEqualTo(expected.getEmail());
        softly.assertThat(other.getLogin()).isEqualTo(saved.getLogin());
        softly.assertThat(other.getPassword()).isEqualTo(saved.getPassword());

        ModelValidators.validateUser(softly, result.getContent(), other);

    }



}