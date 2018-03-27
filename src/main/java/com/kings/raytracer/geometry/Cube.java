package com.kings.raytracer.geometry;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kings.raytracer.auxiliary.Ray;
import com.kings.raytracer.utility.MathUtils;

public class Cube extends Figure{
    private double[] p0, p1, p2, p3;
    private Rectangle[] rectangles = new Rectangle[6];
    private Rectangle currentIntersectingRectangle = null;
    private int intersectingRectangleIndex = 0;


    public Cube(@JsonProperty("p0")double[] p0,
                @JsonProperty("p1")double[] p1,
                @JsonProperty("p2")double[] p2,
                @JsonProperty("p3")double[] p3,
                @JsonProperty("diffuse")double[] diffuse,
                @JsonProperty("reflectance") double reflectance,
                @JsonProperty("surfaceType") String surfaceType,
                @JsonProperty("ambient") double[] ambient,
                @JsonProperty("shininess") double shininess,
                @JsonProperty("emission") double[] emission,
                @JsonProperty("checkersDiffuse1") double[] checkersDiffuse1,
                @JsonProperty("checkersDiffuse2") double[] checkersDiffuse2,
                @JsonProperty("specular") double[] specular) {
        super(diffuse,reflectance,surfaceType, ambient, shininess, emission, checkersDiffuse1, checkersDiffuse2, specular);
        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        setAdditionalValues();
    }

    @Override

    public double[] getNormal(double[] point) throws Exception {

        return currentIntersectingRectangle.getNormal(point);

    }


    @Override

    public double intersect(Ray ray) {

        double minDistance = Double.POSITIVE_INFINITY;

        for (int i = 0; i < rectangles.length; i++) {
            double t = rectangles[i].intersect(ray);

            if (t < minDistance) {
                minDistance = t;
                currentIntersectingRectangle = rectangles[i];
                intersectingRectangleIndex = i;

            }

        }

        return minDistance;

    }

    public void setAdditionalValues(){
        double[] p0_p1 = MathUtils.calcPointsDiff(p0, p1);
        double[] p0_p3 = MathUtils.calcPointsDiff(p0, p3);

        rectangles[0] = new Rectangle(p0, p1, p2, new double[]{0.8, 0.8, 0.8}, 0.0F, "Normal",
                new double[]{0.1F, 0.1F, 0.1F}, 100F, new double[]{0,0,0},
                new double[]{1.0F, 1.0F, 1.0F}, new double[]{0.1F, 0.1F, 0.1F}, new double[]{1.0F, 1.0F, 1.0F});        // Front facing rectangle
        rectangles[1] = new Rectangle(p0, p2, p3, new double[]{0.8, 0.8, 0.8},0.0F, "Normal",
                new double[]{0.1F, 0.1F, 0.1F}, 100F, new double[]{0,0,0},
                new double[]{1.0F, 1.0F, 1.0F}, new double[]{0.1F, 0.1F, 0.1F}, new double[]{1.0F, 1.0F, 1.0F});        // Left facing rectangle
        rectangles[2] = new Rectangle(p0, p3, p1, new double[]{0.8, 0.8, 0.8},0.0F, "Normal",
                new double[]{0.1F, 0.1F, 0.1F}, 100F, new double[]{0,0,0},
                new double[]{1.0F, 1.0F, 1.0F}, new double[]{0.1F, 0.1F, 0.1F}, new double[]{1.0F, 1.0F, 1.0F});        // Bottom facing rectangle
        rectangles[3] = new Rectangle(p1, MathUtils.addPoints(p3, p0_p1), MathUtils.addPoints(p2, p0_p1), new double[]{0.8, 0.8, 0.8},0.0F, "Normal",
                new double[]{0.1F, 0.1F, 0.1F}, 100F, new double[]{0,0,0},
                new double[]{1.0F, 1.0F, 1.0F}, new double[]{0.1F, 0.1F, 0.1F}, new double[]{1.0F, 1.0F, 1.0F});        // Right facing rectangle
        rectangles[4] = new Rectangle(p2, MathUtils.addPoints(p2, p0_p1), MathUtils.addPoints(p2, p0_p3), new double[]{0.8, 0.8, 0.8},0.0F, "Normal",
                new double[]{0.1F, 0.1F, 0.1F}, 100F, new double[]{0,0,0},
                new double[]{1.0F, 1.0F, 1.0F}, new double[]{0.1F, 0.1F, 0.1F}, new double[]{1.0F, 1.0F, 1.0F});        // Top facing rectangle
        rectangles[5] = new Rectangle(p3, MathUtils.addPoints(p2, p0_p3), MathUtils.addPoints(p3, p0_p1), new double[]{0.8, 0.8, 0.8},0.0F, "Normal",
                new double[]{0.1F, 0.1F, 0.1F}, 100F, new double[]{0,0,0},
                new double[]{1.0F, 1.0F, 1.0F}, new double[]{0.1F, 0.1F, 0.1F}, new double[]{1.0F, 1.0F, 1.0F});        // Back facing rectangle

    }

    @Override
    public double[] getTexturePoints(double[] point) {
        return rectangles[intersectingRectangleIndex].getTexturePoints(point);
    }
}
