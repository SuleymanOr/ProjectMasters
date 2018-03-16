package com.kings.raytracer.controller;

import com.kings.raytracer.service.ImageRender;

<<<<<<< HEAD
=======
//import java.util.stream.Collectors;

import com.kings.raytracer.entity.RayTracingImage;
import com.kings.raytracer.service.ImageRender;
>>>>>>> image_rend
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
<<<<<<< HEAD
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
=======
>>>>>>> image_rend
import java.util.Base64;
import java.util.List;


@RestController
public class RayTracerController {

    @Autowired
    ImageRender imageRender;

    @PostMapping("/api/rayTracerJson")
<<<<<<< HEAD
    public ResponseEntity<?> getSearchResultViaAjax() {
=======
    public ResponseEntity<?> getSearchResultViaAjax(@Valid @RequestBody RayTracingImage data, Errors errors) {
>>>>>>> image_rend

        byte[] result = imageRender.renderImage();

        return ResponseEntity.ok(Base64.getEncoder().encodeToString(result));

    }

}
