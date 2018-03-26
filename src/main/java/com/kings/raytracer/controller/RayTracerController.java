package com.kings.raytracer.controller;

import com.kings.raytracer.auxiliary.Camera;
import com.kings.raytracer.geometry.Figure;
import com.kings.raytracer.geometry.Sphere;
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

//////////////////////////////////////////// HARDCODED VALUES  ////////////////////////////////////////////


//        List<Figure> figures = new ArrayList<>();
//        figures.add(new Sphere(new double[]{0,0.5,0}, 0.5, new double[]{0.2F, 0.3F, 0.1F},
//                0.5, "Normal",
//                new double[]{0.1F, 0.1F, 0.1F},
//                100F,
//                new double[]{0, 0, 0},
//                new double[]{1.0F, 1.0F, 1.0F},
//                new double[]{0.1F, 0.1F, 0.1F},
//                new double[]{1.0F, 1.0F, 1.0F}
//                ));
//        List<Light> lights = new ArrayList<>();
//        Light lightDirected = new Light(new double[]{0, 1,-1}, new double[]{1,1,1});
//        lights.add(lightDirected);
//        Scene scene = new Scene(figures, lights, new double[]{0,0,0},new double[]{1,1,1},1,1280,800);
//        Camera camera = new Camera(new double[]{0,0,2}, new double[]{0,0,0}, new double[]{0,1,0}, 1, 2);

        Camera camera = data.getCamera();
        Scene scene = new Scene(mFigures, mLights, backgroundColor, ambientLight, superSampleValue, imageWidth, imageHeight);

        byte[] result = imageRender.renderImage(scene, camera);

        return ResponseEntity.ok(Base64.getEncoder().encodeToString(result));

    }

}
