package com.kings.raytracer.service;

import com.kings.raytracer.auxiliary.Intersection;
import com.kings.raytracer.auxiliary.Ray;
import com.kings.raytracer.geometry.Figure;
import com.kings.raytracer.geometry.Triangle;
import com.kings.raytracer.light.Light;
import com.kings.raytracer.utility.Scene;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class RayServiceTest {

    RayService rayService = new RayService();


    @Test
    void testFindIntersection() throws Exception {
        Ray ray = new Ray(new double[]{0,0,2}, new double[]{0,0,-1},1);
        List<Figure> figures = new ArrayList<>();
        Triangle triangle = new Triangle(new double[]{1,0,0}, new double[]{0,0,1}, new double[]{0,1,0}, new double[]{0,1,0}, 0, "surfaceType",null,0,null,null,null,null);
        figures.add(triangle);

        List<Light> lights = new ArrayList<>();
        Scene scene = new Scene(figures, lights, new double[]{0.5,0.5,1}, new double[]{1,1,1},1, 1280, 800);

        Intersection result = rayService.findIntersection(ray,scene);
        Assertions.assertEquals(new Intersection(Double.POSITIVE_INFINITY, null), result);
    }

    @Test
    void testGetObjectColor() throws Exception {
        Ray ray = new Ray(new double[]{0,0,2}, new double[]{0,0,-1},1);
        List<Figure> figures = new ArrayList<>();
        Triangle triangle = new Triangle(new double[]{0,0,1}, new double[]{1,0,0}, new double[]{0,0,1}, new double[]{1,0,0}, 0, "surfaceType",null,0,null,null,null,null);
        figures.add(triangle);

        List<Light> lights = new ArrayList<>();
        Scene scene = new Scene(figures, lights, new double[]{0.5,0.5,1}, new double[]{1,1,1},1, 1280, 800);
        double[] result = rayService.getObjectColor(ray, new Intersection(0d, null), 0, scene);
        Assertions.assertArrayEquals(new double[]{0.5,0.5,1}, result);
    }
}

