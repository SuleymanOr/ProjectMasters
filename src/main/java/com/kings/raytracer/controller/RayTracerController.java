package com.kings.raytracer.controller;

import com.kings.raytracer.service.ImageRender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@RestController
public class RayTracerController {

    @Autowired
    ImageRender imageRender;

    @PostMapping("/api/rayTracerJson")
    public ResponseEntity<?> getSearchResultViaAjax() {

        byte[] result = imageRender.renderImage();

        return ResponseEntity.ok(Base64.getEncoder().encodeToString(result));

    }

}
