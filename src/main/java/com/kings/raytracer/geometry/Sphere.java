package com.kings.raytracer.geometry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
        return intersectGeometric(ray);
    }

    private double intersectGeometric(Ray ray) {

        // Note that locals are named according to the equations in the lecture notes.
        double[] L = MathUtils.calcPointsDiff(ray.getPosition(), center);
        double[] V = ray.getDirection();

        double tCA = MathUtils.dotProduct(L, V);

        if (tCA < 0) {
            return Double.POSITIVE_INFINITY;
        }

        double LSquare = MathUtils.dotProduct(L, L);

        double dSquare = LSquare - MathUtils.sqr(tCA);
        double radiusSquare = MathUtils.sqr(radius);

        if (dSquare > radiusSquare) {
            // In this case the ray misses the sphere
            return Double.POSITIVE_INFINITY;
        }

        double tHC = Math.sqrt(radiusSquare - dSquare);

        if (MathUtils.dotProduct(L, L) < LSquare) {

            return tCA + tHC;
        } else {
            return tCA - tHC;
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

        if (Math.abs(v) > 1) v -= 1 * Math.signum(v);
        v = Math.acos(v);

        double u = rp[0] / (radius * Math.sin(v));

        if (Math.abs(u) > 1) u = Math.signum(u);
        u = Math.acos(u);

        if (rp[1] < 0)
            u = -u;
        if (rp[2] < 0)
            v = v + Math.PI;

        if (Double.isNaN(u)) {
            int a = 0;
            a++;
        }

        u = (u / (2 * Math.PI));
        v = (v / Math.PI);

        if (u > 1) u -= 1;
        if (u < 0) u += 1;

        if (v > 1) v -= 1;
        if (v < 0) v += 1;

        return new double[]{u, v};
    }
}

