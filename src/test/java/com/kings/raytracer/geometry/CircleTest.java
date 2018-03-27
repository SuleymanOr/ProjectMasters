package com.kings.raytracer.geometry;

import com.kings.raytracer.auxiliary.Ray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CircleTest {

    Circle circle = new Circle(new double[]{1,1,0}, 1, new double[]{0,1,1}, new double[]{1,1,1}, 0.5, "surfaceType", new double[]{1,1,1}, 0d, new double[]{0,1,1}, new double[]{1,1,0}, new double[]{0,1,1}, new double[]{0,0,0});

    @Test
    void testIntersect() {
        double result = circle.intersect(new Ray(new double[]{1,0,1}, new double[]{0,1,1}, 0.5));
        Assertions.assertEquals(Double.POSITIVE_INFINITY, result);
    }

    @Test
    void testGetNormal() throws Exception {
        double[] result = circle.getNormal(new double[]{0,1,1});
        Assertions.assertArrayEquals(new double[]{0,1,1}, result);
    }


    @Test
    void testGetDiffuse() {
        double[] result = circle.getDiffuse();
        Assertions.assertArrayEquals(new double[]{1,1,1}, result);
    }

    @Test
    void testGetAmbient() {
        double[] result = circle.getAmbient();
        Assertions.assertArrayEquals(new double[]{1,1,1}, result);
    }

    @Test
    void testGetEmission() {
        double[] result = circle.getEmission();
        Assertions.assertArrayEquals(new double[]{0,1,1}, result);
    }

    @Test
    void testGetSpecular() {
        double[] result = circle.getSpecular();
        Assertions.assertArrayEquals(new double[]{0,0,0}, result);
    }


    @Test
    void testGetColorAt() {
        double[] result = circle.getColorAt(new double[]{0,0,0});
        Assertions.assertArrayEquals(new double[]{1,1,1}, result);
    }
}

