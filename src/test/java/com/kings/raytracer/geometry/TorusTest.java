package com.kings.raytracer.geometry;

import com.kings.raytracer.auxiliary.Ray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TorusTest {

    Torus torus = new Torus(new double[]{1,1,0}, 1, 1, new double[]{1,0,1}, 0.2, "surfaceType", new double[]{1,1,1}, 0d, new double[]{1,1,0}, new double[]{1,0,0}, new double[]{0d}, new double[]{0d});

    @Test
    void testGetNormal() throws Exception {
        double[] result = torus.getNormal(new double[]{1,0,1});
        Assertions.assertArrayEquals(new double[]{0.12309149097933272,0.12309149097933272,0.9847319278346618}, result);
    }


    @Test
    void testIntersect() {
        double result = torus.intersect(new Ray(new double[]{1,1,0}, new double[]{1,0,0}, 0.2));
        Assertions.assertEquals(-1.4141003182998758E-16, result);
    }

    @Test
    void testGetDiffuse() {
        double[] result = torus.getDiffuse();
        Assertions.assertArrayEquals(new double[]{1,0,1}, result);
    }

    @Test
    void testGetAmbient() {
        double[] result = torus.getAmbient();
        Assertions.assertArrayEquals(new double[]{1,1,1}, result);
    }

    @Test
    void testGetEmission() {
        double[] result = torus.getEmission();
        Assertions.assertArrayEquals(new double[]{1,1,0}, result);
    }

    @Test
    void testGetSpecular() {
        double[] result = torus.getSpecular();
        Assertions.assertArrayEquals(new double[]{0d}, result);
    }


    @Test
    void testGetColorAt() {
        double[] result = torus.getColorAt(new double[]{0d});
        Assertions.assertArrayEquals(new double[]{1,0,1}, result);
    }
}

