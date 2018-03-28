package com.kings.raytracer.controller;

import com.kings.raytracer.auxiliary.Camera;
import com.kings.raytracer.entity.RayTracingImage;
import com.kings.raytracer.geometry.Figure;
import com.kings.raytracer.light.Light;
import com.kings.raytracer.service.ImageRender;
import com.kings.raytracer.utility.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Base64;
import java.util.List;

/*Controller responsible for the handling the Rest Service called
* by the Ajax from the GUI*/
@RestController
public class RayTracerController {

    @Autowired
    ImageRender imageRender;

    /*Used for parsing the values from the gui into the raytracing algortihm*/
    @PostMapping("/api/rayTracerJson")
    public ResponseEntity<?> getSearchResultViaAjax(@Valid @RequestBody RayTracingImage data) {


        List<Figure> mFigures = data.getFigures();
        List<Light> mLights = data.getLights();


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

