package com.kings.raytracer.controller;


//import java.util.stream.Collectors;

import com.kings.raytracer.entity.RayTracingImage;
import com.kings.raytracer.service.ImageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Base64;


@RestController
public class RayTracerController {

    @Autowired
    ImageRender imageRender;

    @PostMapping("/api/rayTracerJson")
    public ResponseEntity<?> getSearchResultViaAjax(@Valid @RequestBody RayTracingImage data, Errors errors) {

        byte[] result = imageRender.renderImage();

        return ResponseEntity.ok(Base64.getEncoder().encodeToString(result));

    }

}
