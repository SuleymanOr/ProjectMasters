package com.kings.raytracer.controller;


//import java.util.stream.Collectors;

import com.kings.raytracer.auxiliary.Camera;
import com.kings.raytracer.entity.AjaxResponseBody;
import com.kings.raytracer.entity.RayTracingImage;
import com.kings.raytracer.geometry.Cylinder;
import com.kings.raytracer.geometry.Figure;
import com.kings.raytracer.geometry.Sphere;
import com.kings.raytracer.light.Light;
import com.kings.raytracer.light.LightDirected;
import com.kings.raytracer.light.LightPoint;
import com.kings.raytracer.service.ImageRender;
import com.kings.raytracer.service.RayTracer;
import com.kings.raytracer.utility.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
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
    public ResponseEntity<?> getSearchResultViaAjax(@Valid @RequestBody RayTracingImage data, Errors errors) {

        byte[] result = imageRender.renderImage();

        return ResponseEntity.ok(Base64.getEncoder().encodeToString(result));

    }

}
