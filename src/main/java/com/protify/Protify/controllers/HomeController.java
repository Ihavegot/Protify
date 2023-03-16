package com.protify.Protify.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String homeEndpoint(){
        return "Protify Music Player API";
    }
}
