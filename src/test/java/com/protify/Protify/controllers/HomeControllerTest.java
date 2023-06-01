package com.protify.Protify.controllers;

import static org.junit.jupiter.api.Assertions.*;

import com.protify.Protify.ProtifyApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ProtifyApplication.class)
@AutoConfigureMockMvc
class HomeControllerTest {
 @Autowired
    private MockMvc mvc;

    @Test
    void get() {

           mvc.perform(get("/").accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
             .andExpect(jsonPath("$._links.songs").value("http://localhost/songs"))
             .andExpect(jsonPath("$._links.playlists").value("http://localhost/playlists"));
    }
}