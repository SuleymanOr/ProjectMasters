package com.kings.raytracer.controller;

import com.kings.raytracer.auxiliary.Camera;
import com.kings.raytracer.geometry.Figure;
import com.kings.raytracer.geometry.Sphere;
import com.kings.raytracer.light.Light;
import com.kings.raytracer.light.LightDirected;
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

//

//        values for the camera

        double[] eye = data.getEye();
        double[] lookAt = data.getLookAt();
        double[] upDirection = data.getUpDirection();
        double cameraScreenDist = data.getCameraScreenDist();
        double cameraScreenWidth = data.getCameraScreenWidth();

//


//////////////////////////////////////////// HARDCODED VALUES  ////////////////////////////////////////////

        List<Figure> figures = new ArrayList<>();
        figures.add(new Sphere(new double[]{0,0.5,0}, 0.5, new double[]{0.6F, 0.5F, 1F}, 0.5, "Normal"));
        List<Light> lights = new ArrayList<>();
        LightDirected lightDirected = new LightDirected(new double[]{0, 1,-1}, new double[]{1,1,1});
        lights.add(lightDirected);
        Scene scene = new Scene(figures, lights, new double[]{0.5,0.5,1}, new double[]{1,1,1},1, 1280, 800);
        Camera camera = new Camera(new double[]{0,0,2}, new double[]{0,0,0}, new double[]{0,1,0}, 1, 2);

        byte[] result = imageRender.renderImage(scene, camera);

        List<Figure> radius = data.getFigures();
        int screen = data.getScreenHeight();

        System.out.println(screen);
        System.out.println(radius.get(0).getReflectance());

        return ResponseEntity.ok(Base64.getEncoder().encodeToString(result));

    }

}
