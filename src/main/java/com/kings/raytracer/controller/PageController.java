package com.kings.raytracer.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping(value="/")
    public String homepage(){
        return "index.html";
    }
}
