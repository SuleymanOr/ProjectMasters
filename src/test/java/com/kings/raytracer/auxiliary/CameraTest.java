package com.kings.raytracer.auxiliary;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CameraTest {

    Camera camera = new Camera(new double[]{0,0,2}, new double[]{0,0,0}, new double[]{0,1,0}, 1, 2);

    @Test
    void testGetEye() {
        double[] result = camera.getEye();
        Assertions.assertArrayEquals(new double[]{0,0,2}, result);
    }

    @Test
    void testGetLookAt() {
        double[] result = camera.getLookAt();
        Assertions.assertArrayEquals(new double[]{0,0,0}, result);
    }

    @Test
    void testGetUpDirection() {
        double[] result = camera.getUpDirection();
        Assertions.assertArrayEquals(new double[]{0,1,0}, result);
    }


    @Test
    void testGetDirection() {
        double[] result = camera.getDirection();
        Assertions.assertArrayEquals(new double[]{0,0,-1}, result);
    }

    @Test
    void testGetViewPlaneUp() {
        double[] result = camera.getViewPlaneUp();
        Assertions.assertArrayEquals(new double[]{0,1,0}, result);
    }
}
