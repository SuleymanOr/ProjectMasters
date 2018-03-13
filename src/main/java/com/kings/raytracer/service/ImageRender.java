package com.kings.raytracer.service;

import com.kings.raytracer.auxiliary.Camera;
import com.kings.raytracer.geometry.Figure;
import com.kings.raytracer.geometry.Sphere;
import com.kings.raytracer.light.Light;
import com.kings.raytracer.light.LightDirected;
import com.kings.raytracer.utility.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageRender {

    @Autowired
    RayTracer rayTracer;

    public byte[] renderImage(){


        ByteArrayOutputStream image = new ByteArrayOutputStream();

        List<Figure> figures = new ArrayList<>();
        figures.add(new Sphere(new double[]{0,0.5,0}, 0.5, new double[]{0.6F, 0.5F, 1F}, 0.5, "Normal"));
        List<Light> lights = new ArrayList<>();
        LightDirected lightDirected = new LightDirected(new double[]{0, 1,-1}, new double[]{1,1,1});
        lights.add(lightDirected);
        Scene scene = new Scene(figures, lights, new double[]{0.5,0.5,1}, new double[]{1,1,1},1, 1280, 800);
        Camera camera = new Camera(new double[]{0,0,2}, new double[]{0,0,0}, new double[]{0,1,0}, 1, 2);

        try {
            ImageIO.write(rayTracer.renderImage(scene, camera), "PNG", image);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return image.toByteArray();

    }
}

