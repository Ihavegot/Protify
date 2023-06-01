package com.protify.Protify.controllers;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.protify.Protify.ProtifyApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.client.Hop;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ProtifyApplication.class)
@AutoConfigureMockMvc
class HomeControllerTest {
 @Autowired
    private MockMvc mvc;

    @Test
    void getHome() throws Exception {



           mvc.perform(get("/").accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
             .andExpect(jsonPath("$._links.songs.href").value("http://localhost/songs{?page,size,sort}"))
             .andExpect(jsonPath("$._links.playlists.href").value("http://localhost/playlists{?page,size,sort}"));
    }
}