package com.kings.raytracer.geometry;

import com.kings.raytracer.auxiliary.Ray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CylinderTest {

    Cylinder cylinder = new Cylinder(new double[]{1,0,1}, new double[]{0,1,1}, 0.2, 0.2, new double[]{1,1,1}, 0d, "surfaceType", new double[]{0,1,1}, 0d, new double[]{0,1,1}, new double[]{1,1,1}, new double[]{1,1,1}, new double[]{0,1,1});

    @Test
    void testIntersect() {
        double result = cylinder.intersect(new Ray(new double[]{1,1,1}, new double[]{0,1,1}, 0d));
        Assertions.assertEquals(Double.POSITIVE_INFINITY, result);
    }

    @Test
    void testGetDiffuse() {
        double[] result = cylinder.getDiffuse();
        Assertions.assertArrayEquals(new double[]{1,1,1}, result);
    }

    @Test
    void testGetAmbient() {
        double[] result = cylinder.getAmbient();
        Assertions.assertArrayEquals(new double[]{0,1,1}, result);
    }

    @Test
    void testGetEmission() {
        double[] result = cylinder.getEmission();
        Assertions.assertArrayEquals(new double[]{0,1,1}, result);
    }

    @Test
    void testGetSpecular() {
        double[] result = cylinder.getSpecular();
        Assertions.assertArrayEquals(new double[]{0,1,1}, result);
    }

    @Test
    void testGetColorAt() {
        double[] result = cylinder.getColorAt(new double[]{0d});
        Assertions.assertArrayEquals(new double[]{1,1,1}, result);
    }
}
