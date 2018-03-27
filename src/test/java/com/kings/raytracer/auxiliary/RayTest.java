package com.kings.raytracer.auxiliary;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RayTest {

    Ray ray = new Ray(new double[]{1,1,1}, new double[]{0,1,1}, 0.5);

    @Test
    void testNormalize() {
        ray.normalize();
    }

    @Test
    void testGetEndPoint() {
        double[] result = ray.getEndPoint();
        Assertions.assertArrayEquals(new double[]{1,1.5,1.5}, result);
    }

    @Test
    void testEquals() {
        boolean result = ray.equals(null);
        Assertions.assertEquals(false, result);
    }

    @Test
    void testGetPosition() {
        double[] result = ray.getPosition();
        Assertions.assertArrayEquals(new double[]{1,1,1}, result);
    }

    @Test
    void testGetDirection() {
        double[] result = ray.getDirection();
        Assertions.assertArrayEquals(new double[]{0,1,1}, result);
    }
}

