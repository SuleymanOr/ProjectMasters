package com.kings.raytracer.geometry;

import com.kings.raytracer.auxiliary.Ray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RectangleTest {

    Rectangle rectangle = new Rectangle(new double[]{1,0,1}, new double[]{0,1,1}, new double[]{1,1,1}, new double[]{1,0,0}, 0.2, "surfaceType", new double[]{1,1,1}, 0.2, new double[]{0,1,1}, new double[]{1,1,1}, new double[]{0,1,1}, new double[]{0,1,1});

    @Test
    void testIntersect() {
        double result = rectangle.intersect(new Ray(new double[]{1,0,1}, new double[]{1,0,1}, 0.2));
        Assertions.assertEquals(Double.POSITIVE_INFINITY, result);
    }

    @Test
    void testGetNormal() {
        double[] result = rectangle.getNormal(new double[]{0d});
        Assertions.assertArrayEquals(new double[]{0,0,-1}, result);
    }

    @Test
    void testGetTexturePoints() {
        double[] result = rectangle.getTexturePoints(new double[]{0,1,1});
        Assertions.assertArrayEquals(new double[]{1,1.4142135623730951}, result);
    }

    @Test
    void testGetNormal2() {
        double[] result = rectangle.getNormal();
        Assertions.assertArrayEquals(new double[]{0,0,-1}, result);
    }

    @Test
    void testSetAdditionalValues() {
        rectangle.setAdditionalValues();
    }

    @Test
    void testGetPoint0() {
        double[] result = rectangle.getPoint0();
        Assertions.assertArrayEquals(new double[]{1,0,1}, result);
    }

    @Test
    void testGetPoint1() {
        double[] result = rectangle.getPoint1();
        Assertions.assertArrayEquals(new double[]{0,1,1}, result);
    }

    @Test
    void testGetPoint2() {
        double[] result = rectangle.getPoint2();
        Assertions.assertArrayEquals(new double[]{1,1,1}, result);
    }

    @Test
    void testGetPoint3() {
        double[] result = rectangle.getPoint3();
        Assertions.assertArrayEquals(new double[]{0,2,1}, result);
    }

    @Test
    void testGetDiffuse() {
        double[] result = rectangle.getDiffuse();
        Assertions.assertArrayEquals(new double[]{1,0,0}, result);
    }

    @Test
    void testGetAmbient() {
        double[] result = rectangle.getAmbient();
        Assertions.assertArrayEquals(new double[]{1,1,1}, result);
    }

    @Test
    void testGetEmission() {
        double[] result = rectangle.getEmission();
        Assertions.assertArrayEquals(new double[]{0,1,1}, result);
    }

    @Test
    void testGetSpecular() {
        double[] result = rectangle.getSpecular();
        Assertions.assertArrayEquals(new double[]{0,1,1}, result);
    }

    @Test
    void testGetColorAt() {
        double[] result = rectangle.getColorAt(new double[]{0d});
        Assertions.assertArrayEquals(new double[]{1,0,0}, result);
    }
}

