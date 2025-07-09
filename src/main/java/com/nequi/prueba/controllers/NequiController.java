package com.nequi.prueba.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NequiController {
    @GetMapping("/")
    public String hello() {
        return "Hola desde Spring Boot!";
    }

}
