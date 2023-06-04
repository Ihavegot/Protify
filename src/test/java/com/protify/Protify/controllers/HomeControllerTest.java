package com.protify.Protify.controllers;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.protify.Protify.ProtifyApplication;
import com.protify.Protify.models.Songs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.client.Hop;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ProtifyApplication.class)
@AutoConfigureMockMvc
class HomeControllerTest {
 @Autowired
    private MockMvc mvc;


    @Value(value="${local.server.port}")
    private int port;
    private Traverson traverson;

    @BeforeEach
    public void beforeEach(){
        traverson = new Traverson(URI.create("http://localhost:"+port+"/"), MediaTypes.HAL_JSON);

    }

    @ParameterizedTest
    @CsvSource({"songs","playlists","users"})
    void links(String rel) throws Exception {

        PagedModel<Object> page = traverson.follow(Hop.rel(rel)
                        .withParameter("page", 1)
                        .withParameter("sort", "id")
                        .withParameter("size", 30))
                .toObject(new ParameterizedTypeReference<PagedModel<Object>>() {
                });

        Assertions.assertNotNull(page);

    }


}