package com.kings.raytracer.geometry;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kings.raytracer.auxiliary.Ray;
import com.kings.raytracer.utility.MathUtils;

public class Triangle extends Figure {
    private  double[] point0, point1, point2;

    public Triangle(@JsonProperty("point0")double[] point0,
                    @JsonProperty("point1")double[] point1,
                    @JsonProperty("point2")double[] point2,
                    @JsonProperty("diffuse") double[] diffuse,
                    @JsonProperty("reflectance") double reflectance,
                    @JsonProperty("surfaceType") String surfaceType,
                    @JsonProperty("ambient") double[] ambient,
                    @JsonProperty("shininess") double shininess,
                    @JsonProperty("emission") double[] emission,
                    @JsonProperty("checkersDiffuse1") double[] checkersDiffuse1,
                    @JsonProperty("checkersDiffuse2") double[] checkersDiffuse2,
                    @JsonProperty("specular") double[] specular) {
        super(diffuse,reflectance,surfaceType, ambient, shininess, emission, checkersDiffuse1, checkersDiffuse2, specular);
        this.point0 = point0;
        this.point1 = point1;
        this.point2 = point2;
    }

    @Override
    public double intersect(Ray ray) {
        return intersectSolution(ray);
    }

    private double intersectSolution(Ray ray) {
        double[] T = MathUtils.calcPointsDiff(point0,ray.getPosition());

        double[] d1 = MathUtils.calcPointsDiff(point0, point1);
        double[] Q = MathUtils.crossProduct(T,d1);

        double[] d2 = MathUtils.calcPointsDiff(point0, point2);
        double[] P = MathUtils.crossProduct(ray.getDirection(), d2);

        double projection = MathUtils.dotProduct(P,d1);

        Double distance = distanceToIntersection(ray, T, Q, d2, P, projection);
        return distance;
    }

    @Override
    public double[] getNormal(double[] point) {
        double[] d1 = MathUtils.calcPointsDiff(point1, point0);
        double[] d2 = MathUtils.calcPointsDiff(point2, point0);
        double[] normal  = MathUtils.crossProduct(d1,d2);
        MathUtils.normalize(normal);
        return  normal;
    }

    @Override
    public double[] getTexturePoints(double[] point) {
        return new double[0];
    }

    private Double distanceToIntersection(Ray ray, double[] t, double[] q, double[] d2, double[] p, double projection) {
        if (projection >= MathUtils.EPSILON || projection <= -MathUtils.EPSILON) {
            double a =  MathUtils.dotProduct(p, t)/ projection;
            double b = MathUtils.dotProduct(q,ray.getDirection()) / projection;

            if (a >= MathUtils.ZERO  && b >= MathUtils.ZERO && a + b <= MathUtils.UNIT) {
                double distance = MathUtils.dotProduct(q,d2) / projection;
                return distance;
            }
        }
        return Double.NaN;
    }

    public double[] getPoint0() {
        return point0;
    }

    public void setPoint0(double[] point0) {
        this.point0 = point0;
    }

    public double[] getPoint1() {
        return point1;
    }

    public void setPoint1(double[] point1) {
        this.point1 = point1;
    }

    public double[] getPoint2() {
        return point2;
    }

    public void setPoint2(double[] point2) {
        this.point2 = point2;
    }

}