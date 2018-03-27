package com.kings.raytracer.geometry;

import com.kings.raytracer.auxiliary.Ray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConeTest {

    Cone cone = new Cone(new double[]{1,1,0},new double[]{0,1,1},1, 1, new double[]{0,1,1},1, "surfaceType", new double[]{1,1,1}, 0d, new double[]{0,1,1}, new double[]{1,1,0}, new double[]{0,1,1}, new double[]{0,0,0});

    @Test
    void testGetNormal() {
        double[] result = cone.getNormal(new double[]{0,1,1});
        Assertions.assertArrayEquals(new double[]{-0.408248290463863,-0.816496580927726,-0.408248290463863}, result);
    }


    @Test
    void testIntersect() {
        double result = cone.intersect(new Ray(new double[]{1,0,1}, new double[]{0,1,1}, 0.2));
        Assertions.assertEquals(0.642092615934331, result);
    }

    @Test
    void testGetDirection() {
        double[] result = cone.getDirection();
        Assertions.assertArrayEquals(new double[]{0,0.7071067811865475,0.7071067811865475}, result);
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

