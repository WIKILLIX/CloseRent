package com.example.closetrent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador principal para la página de inicio.
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
