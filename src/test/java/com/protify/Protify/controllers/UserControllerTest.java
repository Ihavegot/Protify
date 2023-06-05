package com.protify.Protify.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.protify.Protify.ModelValidators;
import com.protify.Protify.ProtifyApplication;
import com.protify.Protify.adapter.FormsTraverson;
import com.protify.Protify.dtos.UserDto;
import com.protify.Protify.models.Playlist;
import com.protify.Protify.models.User;
import com.protify.Protify.repository.PlaylistRepository;
import com.protify.Protify.service.UserService;
import lombok.Getter;
import lombok.Setter;
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
import org.springframework.data.domain.Pageable;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.client.Hop;
import org.springframework.hateoas.config.HypermediaRestTemplateConfigurer;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ProtifyApplication.class)
@ExtendWith(SoftAssertionsExtension.class)
class UserControllerTest {

    

    
    @Value(value="${local.server.port}")
    private int port;
    private FormsTraverson traverson;


    @Autowired
    private UserService userService;

    @BeforeEach
    public void beforeEach(){

        userService.deleteAll();
        traverson = new FormsTraverson(URI.create("http://localhost:"+port+"/"),
                configurer


        );

    }
    @Autowired
    private HypermediaRestTemplateConfigurer configurer;

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
    


    @Autowired
    private ObjectMapper mapper;

    @Test
    void postUser() throws Exception {

    var link =  traverson.follow("users").asLink().toUri();



        UserDto expected = new UserDto();
        expected.setLogin("user");
        expected.setPassword("password");
        expected.setEmail("user@example.com");


        //when
        var request = traverson.follow("users", "$._templates.default.target");
        //then
        softly.assertThat(request.<String>toObject("$._templates.default.method")).isEqualTo("POST");

        EntityModel<User> result = request
                .post(expected, new ParameterizedTypeReference<>() {
        });


        User other = userService.findAll(Pageable.unpaged()).stream().findFirst().orElseThrow();
        softly.assertThat(other.getEmail()).isEqualTo(expected.getEmail());
        softly.assertThat(other.getLogin()).isEqualTo(expected.getLogin());
        softly.assertThat(other.getPassword()).isEqualTo(expected.getPassword());

        ModelValidators.validateUser(softly, result.getContent(), other);

    }




    @Test
    void deleteUser() throws Exception {
         var user = userService.save(User.builder().build());


        //when
        var request = traverson.follow("users", "$._embedded.users[0]._links.self.href");
        //then
        softly.assertThat(request.<String>toObject("$._templates.default.method")).isEqualTo("DELETE");

        EntityModel<User> result = request
                .delete(new ParameterizedTypeReference<>() {
        });




        softly.assertThat(userService.findAll(Pageable.unpaged())).hasSize(0);

            ModelValidators.validateUser(softly, result.getContent(),user);

    }

    @Test
    void putUser_update() throws Exception {
//        given

        userService.save(User.builder().email("before").login("before").password("before").build());

        UserDto expected = new UserDto();
        expected.setLogin("user");
        expected.setPassword("password");
        expected.setEmail("user@example.com");


//when
var request =  traverson.follow("users", "$._embedded.users[0]._links.self.href");
//then


        softly.assertThat(request.<String>toObject("$._templates.putUser.method")).isEqualTo("PUT");

        EntityModel<User> result = request
                .put(expected, new ParameterizedTypeReference<>() {
                })
                ;


        User other = userService.findAll(Pageable.unpaged()).stream().findFirst().orElseThrow();
        softly.assertThat(other.getEmail()).isEqualTo(expected.getEmail());
        softly.assertThat(other.getLogin()).isEqualTo(expected.getLogin());
        softly.assertThat(other.getPassword()).isEqualTo(expected.getPassword());

        ModelValidators.validateUser(softly, result.getContent(), other);
    }





    @Test
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
                .patch(expected, new ParameterizedTypeReference<>() {
                })
        ;


        User other = userService.findAll(Pageable.unpaged()).stream().findFirst().orElseThrow();
        softly.assertThat(other.getEmail()).isEqualTo(expected.getEmail());
        softly.assertThat(other.getLogin()).isEqualTo(saved.getLogin());
        softly.assertThat(other.getPassword()).isEqualTo(saved.getPassword());

        ModelValidators.validateUser(softly, result.getContent(), other);

    }



}