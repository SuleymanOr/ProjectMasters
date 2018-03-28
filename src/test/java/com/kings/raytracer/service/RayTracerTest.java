package com.kings.raytracer.service;

import com.kings.raytracer.auxiliary.Camera;
import com.kings.raytracer.auxiliary.Intersection;
import com.kings.raytracer.auxiliary.Ray;
import com.kings.raytracer.geometry.Figure;
import com.kings.raytracer.geometry.Sphere;
import com.kings.raytracer.geometry.Triangle;
import com.kings.raytracer.light.Light;
import com.kings.raytracer.utility.Scene;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class RayTracerTest {
    @Mock
    RayService rayService;
    @InjectMocks
    RayTracer rayTracer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    List<Figure> figures = new ArrayList<>();
    List<Light> lights = new ArrayList<>();
    Scene scene = new Scene(figures, lights, new double[]{0.5,0.5,1}, new double[]{1,1,1},1, 1280, 800);
    Camera camera = new Camera(new double[]{0,0,2}, new double[]{0,0,0}, new double[]{0,1,0}, 1, 2);

    @Test
    void testGetRayHits() {
        Triangle triangle = new Triangle(new double[]{1,0,0}, new double[]{1,0,0}, new double[]{1,0,0}, new double[]{1,0,0}, 0.4, "surfaceType",null,0,null,null,null,null);
        figures.add(triangle);
        when(rayService.findIntersection(any(), any())).thenReturn(new Intersection(0d, triangle));
        try {
            when(rayService.getObjectColor(any(), any(), anyInt(), any())).thenReturn(new double[]{0,0,0});
        } catch (Exception e) {
            e.printStackTrace();
        }

        int result = 0;
        try {
            result = rayTracer.getRayHits(scene, camera, 0, 0, 0, new double[]{0,0,0});
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(1, result);
    }

    @Test
    void testBuildRay() {
        Ray result = null;
        Triangle triangle = new Triangle(new double[]{1,0,0}, new double[]{0,1,0}, new double[]{0,1,0}, new double[]{0,1,0}, 0, "surfaceType",null,0,null,null,null,null);
        figures.add(triangle);

        Light lightImpl = new Light(new double[]{0, 1,-1}, new double[]{1,1,1});
        lights.add(lightImpl);
        try {
            result = rayTracer.buildRay(0, 0, 0, 0, scene, camera);
            Ray ray = new Ray(new double[]{0,0,2}, new double[]{0,0,-1},1);
            Assertions.assertEquals(ray, result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

