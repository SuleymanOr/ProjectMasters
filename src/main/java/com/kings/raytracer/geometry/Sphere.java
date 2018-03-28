package com.kings.raytracer.geometry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kings.raytracer.auxiliary.Ray;
import com.kings.raytracer.utility.MathUtils;

public class Sphere extends Figure {

    private double[] center;
    private double radius;

    @JsonCreator
    public Sphere(@JsonProperty("center")double[] center,
                  @JsonProperty("radius") double radius,
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
        this.center = center;
        this.radius = radius;
    }

    @Override
    public double intersect(Ray ray) {
        return intersectSolution(ray);
    }

    private double intersectSolution(Ray ray) {

        double[] L = MathUtils.calcPointsDiff(ray.getPosition(), center);
        double[] V = ray.getDirection();

        double c = MathUtils.dotProduct(L, V);

        if (c < MathUtils.ZERO) {
            return Double.POSITIVE_INFINITY;
        }

        return returnDistanceFromIntersection(L, c);
    }

    private double returnDistanceFromIntersection(double[] l, double c) {
        double LSquare = MathUtils.dotProduct(l, l);

        double dSquare = LSquare - MathUtils.sqr(c);
        double radiusSquare = MathUtils.sqr(radius);

        if (dSquare > radiusSquare) {
            return Double.POSITIVE_INFINITY;
        }

        double h = Math.sqrt(radiusSquare - dSquare);

        if (MathUtils.dotProduct(l, l) < LSquare) {

            return c + h;
        } else {
            return c - h;
        }
    }

    @Override
    public double[] getNormal(double[] point) {
        double[] normal = MathUtils.calcPointsDiff(center, point);
        MathUtils.normalize(normal);

        return normal;
    }

    @Override
    public double[] getTexturePoints(double[] point) {
        double[] rp = MathUtils.calcPointsDiff(center, point);

        double v = rp[2] / radius;

        if (Math.abs(v) > MathUtils.UNIT) v -= 1 * Math.signum(v);
        v = Math.acos(v);

        double u = rp[0] / (radius * Math.sin(v));

        if (Math.abs(u) > MathUtils.UNIT) u = Math.signum(u);
        u = Math.acos(u);

        if (rp[1] < MathUtils.ZERO)
            u = -u;
        if (rp[2] < MathUtils.ZERO)
            v = v + Math.PI;

        u = (u / (2 * Math.PI));
        v = (v / Math.PI);

        if (u > 1) u -= 1;
        if (u < 0) u += 1;

        if (v > 1) v -= 1;
        if (v < 0) v += 1;

        return new double[]{u, v};
    }
}

