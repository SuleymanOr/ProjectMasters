package com.kings.raytracer.geometry;

import com.kings.raytracer.auxiliary.Ray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SphereTest {

    Sphere sphere = new Sphere(new double[]{0,1,1}, 0.5, new double[]{1,1,1}, 0.5, "surfaceType",new double[]{1,1,1}, 0d, new double[]{0,1,1}, new double[]{1,1,0}, new double[]{0,1,1}, new double[]{0,0,0});

    @Test
    void testIntersect() {
        double result = 0;
        try {
            result = sphere.intersect(new Ray(new double[]{0, 0, 0.5},new double[]{0, 0, 0.5},0.5));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(Double.POSITIVE_INFINITY, result);
    }

    @Test
    void testGetNormal() {
        double[] result = sphere.getNormal(new double[]{1,0,0});
        Assertions.assertArrayEquals(new double[]{0.5773502691896258,-0.5773502691896258,-0.5773502691896258}, result);
    }

    @Test
    void testGetTexturePoints() {
        double[] result = sphere.getTexturePoints(new double[]{0,0,0});
        Assertions.assertArrayEquals(new double[]{0.75,1}, result);
    }


    @Test
    void testGetDiffuse() {
        double[] result = sphere.getDiffuse();
        Assertions.assertArrayEquals(new double[]{1,1,1}, result);
    }

    @Test
    void testGetColorAt() {
        double[] result = sphere.getColorAt(new double[]{1,0,0});
        Assertions.assertArrayEquals(new double[]{1,1,1}, result);
    }
}

