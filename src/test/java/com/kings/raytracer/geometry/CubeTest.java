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

    @Test
    void testSetAdditionalValues() {
        cube.setAdditionalValues();
    }


    @Test
    void testGetColorAt() {
        when(currentIntersectingRectangle.getDiffuse()).thenReturn(new double[]{0d});
        when(currentIntersectingRectangle.getTexturePoints(new double[]{0,1,1}));
    }
}
