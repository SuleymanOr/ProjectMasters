package com.kings.raytracer.controller;

import com.kings.raytracer.auxiliary.Camera;
import com.kings.raytracer.entity.RayTracingImage;
import com.kings.raytracer.geometry.Cube;
import com.kings.raytracer.geometry.Figure;
import com.kings.raytracer.geometry.Rectangle;
import com.kings.raytracer.geometry.Torus;
import com.kings.raytracer.light.Light;
import com.kings.raytracer.service.ImageRender;

import com.kings.raytracer.utility.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@RestController
public class RayTracerController {

    @Autowired
    ImageRender imageRender;

    @PostMapping("/api/rayTracerJson")
    public ResponseEntity<?> getSearchResultViaAjax(@Valid @RequestBody RayTracingImage data) {

//////////////////////////////////////////// VALUES PASSED FROM FRONT END  ////////////////////////////////////////////

        List<Figure> mFigures = data.getFigures();
        List<Light> mLights = data.getLights();

//        values for the scene

        double[] backgroundColor = data.getBackgroundColor();
        double[] ambientLight = data.getAmbientLight();
        int superSampleValue = data.getSuperSampleValue();
        int imageWidth = data.getScreenWidth();
        int imageHeight = data.getScreenHeight();

        Camera camera = data.getCamera();

        Scene scene = new Scene(mFigures, mLights, backgroundColor, ambientLight, superSampleValue, imageWidth, imageHeight);
        byte[] result = imageRender.renderImage(scene, camera);

        return ResponseEntity.ok(Base64.getEncoder().encodeToString(result));

    }

}

