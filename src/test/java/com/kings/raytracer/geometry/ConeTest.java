package com.kings.raytracer.geometry;

import com.kings.raytracer.auxiliary.Ray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConeTest {

    Cone cone = new Cone(new double[]{1,1,0},new double[]{0,1,1},1, 1, new double[]{0,1,1},1, "surfaceType", new double[]{1,1,1}, 0d, new double[]{0,1,1}, new double[]{1,1,0}, new double[]{0,1,1}, new double[]{0,0,0});

    @Test
    void testIntersect() {
        double result = cone.intersect(new Ray(new double[]{1,0,1}, new double[]{0,1,1}, 0.2));
        Assertions.assertEquals(Double.NaN, result);
    }
    @Test
    void testGetDirection() {
        double[] result = cone.getDirection();
        Assertions.assertArrayEquals(new double[]{0,-0.37139067635410367,-0.9284766908852592}, result);
    }

    @Test
    void testGetDiffuse() {
        double[] result = cone.getDiffuse();
        Assertions.assertArrayEquals(new double[]{0,1,1}, result);
    }

    @Test
    void testGetAmbient() {
        double[] result = cone.getAmbient();
        Assertions.assertArrayEquals(new double[]{1,1,1}, result);
    }

    @Test
    void testGetEmission() {
        double[] result = cone.getEmission();
        Assertions.assertArrayEquals(new double[]{0,1,1}, result);
    }

    @Test
    void testGetSpecular() {
        double[] result = cone.getSpecular();
        Assertions.assertArrayEquals(new double[]{0,0,0}, result);
    }

    @Test
    void testGetColorAt() {
        double[] result = cone.getColorAt(new double[]{0d});
        Assertions.assertArrayEquals(new double[]{0,1,1}, result);
    }
}

