package com.kings.raytracer.geometry;

import com.kings.raytracer.auxiliary.Ray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class CubeTest {

    @Mock
    Rectangle currentIntersectingRectangle;
    @Mock
    Cube cube;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testIntersect() {
        when(currentIntersectingRectangle.intersect(any())).thenReturn(0.2);

        double result = cube.intersect(new Ray(new double[]{1,1,1}, new double[]{1,1,0}, 0.2));
        Assertions.assertEquals(0d, result);
    }

    private double[] specular = {1.0F, 1.0F, 1.0F};
    private double[] ambient = {0.1F, 0.1F, 0.1F};
    private double[] emission = {0, 0, 0};
    private double shininess = 100.0F;
    private double reflectance ;
    private double checkersSize = 0.1F;
    private double[] checkersDiffuse1 = {1.0F, 1.0F, 1.0F};
    private double[] checkersDiffuse2 = {0.1F, 0.1F, 0.1F};


    @Test
    void testSetAdditionalValues() {
        cube.setAdditionalValues(new double[]{0.5,0.3,0.4}, 10, "Normal",
                new double[]{0.1F, 0.1F, 0.1F}, 100.0F, new double[]{0,0,0},
                new double[]{1.0F, 1.0F, 1.0F}, new double[]{0.1F, 0.1F, 0.1F},
                new double[]{1.0F, 1.0F, 1.0F});
    }


    @Test
    void testGetColorAt() {
        when(currentIntersectingRectangle.getDiffuse()).thenReturn(new double[]{0,0,0});
        when(currentIntersectingRectangle.getTexturePoints(new double[]{0,1,1})).thenReturn(new double[]{0,0,0});
    }
}
