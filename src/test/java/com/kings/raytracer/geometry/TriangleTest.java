package com.kings.raytracer.geometry;

import com.kings.raytracer.auxiliary.Ray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TriangleTest {
    Triangle triangle = new Triangle(new double[]{1,0,0}, new double[]{0,0,1}, new double[]{1,0,0}, new double[]{0,1,0}, 0, "surfaceType",new double[]{1,1,1}, 0d, new double[]{0,1,1}, new double[]{1,1,0}, new double[]{0,1,1}, new double[]{0,0,0});

    @Test
    void testIntersect() {
        double result = 0;
        try {
            result = triangle.intersect(new Ray(new double[]{0, 0, 0.5},new double[]{0, 0, 0.5},0.5));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(Double.NaN, result);
    }

    @Test
    void testGetTexturePoints() {
        double[] result = triangle.getTexturePoints(new double[]{0,1,0});
        Assertions.assertArrayEquals(new double[0], result);
    }

    @Test
    void testGetPoint0() {
        double[] result = triangle.getPoint0();
        Assertions.assertArrayEquals(new double[]{1,0,0}, result);
    }

    @Test
    void testGetPoint1() {
        double[] result = triangle.getPoint1();
        Assertions.assertArrayEquals(new double[]{0,0,1}, result);
    }

    @Test
    void testGetPoint2() {
        double[] result = triangle.getPoint2();
        Assertions.assertArrayEquals(new double[]{1,0,0}, result);
    }

    @Test
    void testGetDiffuse() {
        double[] result = triangle.getDiffuse();
        Assertions.assertArrayEquals(new double[]{0,1,0}, result);
    }

    @Test
    void testGetColorAt() {
        double[] result = triangle.getColorAt(new double[]{0,0,1});
        Assertions.assertArrayEquals(new double[]{0,1,0}, result);
    }
}

