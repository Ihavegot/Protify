package com.protify.Protify.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.protify.Protify.ModelValidators;
import com.protify.Protify.ProtifyApplication;
import com.protify.Protify.dtos.UserDto;
import com.protify.Protify.models.Artist;
import com.protify.Protify.models.Playlist;
import com.protify.Protify.models.Songs;
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
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.client.Hop;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ProtifyApplication.class)
@ExtendWith(SoftAssertionsExtension.class)

@AutoConfigureMockMvc
class UserControllerTest {

    
    @Autowired
    private  MockMvc mvc;
    
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



    @Setter
    static  class HalFormsModel {
        @JsonProperty("_templates")

        private Map<String, HalFormModel> templates;

        public HalFormModel get(Object key) {
            return templates.get(key);
        }
    }


    @Setter
    static  class HalFormModel{
        @Getter private String method;
        private String target;

        public Optional<URI> getTarget() throws URISyntaxException {
            if(target == null){
                return  Optional.empty();
            }
            return Optional.of(new URI(target));
        }
    }


    @Autowired
    private ObjectMapper mapper;

    @Test
    void postUser() throws Exception {

    var link =  traverson.follow("users").asLink().toUri();

        HalFormsModel page =  getForms(link)   ;

        HalFormModel action = page.get("default");

        UserDto expected = new UserDto();
        expected.setLogin("user");
        expected.setPassword("password");
        expected.setEmail("user@example.com");


        softly.assertThat(action.getMethod()).isEqualTo("POST");

        EntityModel<User> result = mapper.readValue(mvc
                //when
                .perform(MockMvcRequestBuilders.request(
             action.getMethod(),
                        action.getTarget().orElse(link)
        ).accept(MediaTypes.HAL_JSON).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(expected)
        ))
                //then
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString(), new TypeReference<>() {
        });


        User other = userService.findAll(Pageable.unpaged()).stream().findFirst().orElseThrow();
        softly.assertThat(other.getEmail()).isEqualTo(expected.getEmail());
        softly.assertThat(other.getLogin()).isEqualTo(expected.getLogin());
        softly.assertThat(other.getPassword()).isEqualTo(expected.getPassword());

        ModelValidators.validateUser(softly, result.getContent(), other);

    }

HalFormsModel    getForms(URI link) throws Exception {
     return                  mapper.readValue(mvc.perform(MockMvcRequestBuilders.get(link).accept(MediaTypes.HAL_FORMS_JSON))
                     .andExpect(status().isOk()).andReturn().getResponse().getContentAsString()
             , new TypeReference<>() {
             });
    }



    @Test
    void deleteUser() throws Exception {
         var user = userService.save(User.builder().build());

        var link = traverson.follow("users").follow("$._embedded.users[0]._links.self.href").asLink().toUri();


        HalFormsModel page =  getForms(link)   ;




        HalFormModel action = page.get("default");
        softly.assertThat(action.getMethod()).isEqualTo("DELETE");

        EntityModel<User> result = mapper.readValue(mvc
                //when
                .perform(MockMvcRequestBuilders.request(
                        action.getMethod(),
                                action.getTarget().orElse(link)
                ).accept(MediaTypes.HAL_JSON)
                )
                //then
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString(), new TypeReference<>() {
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


        var link =  traverson.follow("users", "$._embedded.users[0]._links.self.href").asLink().toUri();
        HalFormsModel page =  getForms(link)   ;
        HalFormModel action = page.get("putUser");
        softly.assertThat(action.getMethod()).isEqualTo("PUT");

        EntityModel<User> result = mapper.readValue(mvc
                //when
                .perform(MockMvcRequestBuilders.request(
                        action.getMethod(),
                        action.getTarget().orElse(link)
                ).accept(MediaTypes.HAL_JSON).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(expected)
                ))
                //then
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString(), new TypeReference<>() {
        });


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


        var link =  traverson.follow("users", "$._embedded.users[0]._links.self.href").asLink().toUri();
        HalFormsModel page =  getForms(link)   ;
        HalFormModel action = page.get("patchUser");
        softly.assertThat(action.getMethod()).isEqualTo("PATCH");

        EntityModel<User> result = mapper.readValue(mvc
                //when
                .perform(MockMvcRequestBuilders.request(
                        action.getMethod(),
                        action.getTarget().orElse(link)
                ).accept(MediaTypes.HAL_JSON).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(expected)
                ))
                //then
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString(), new TypeReference<>() {
        });


        User other = userService.findAll(Pageable.unpaged()).stream().findFirst().orElseThrow();
        softly.assertThat(other.getEmail()).isEqualTo(expected.getEmail());
        softly.assertThat(other.getLogin()).isEqualTo(saved.getLogin());
        softly.assertThat(other.getPassword()).isEqualTo(saved.getPassword());

        ModelValidators.validateUser(softly, result.getContent(), other);

    }


}