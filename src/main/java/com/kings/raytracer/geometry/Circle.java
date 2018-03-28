package com.kings.raytracer.geometry;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kings.raytracer.auxiliary.Ray;
import com.kings.raytracer.utility.MathUtils;

public class Circle extends Figure{
    private double[] center;
    private double[] normal;
    private double radius;
    private double[] intersectionPoint = null;
    private double d;
    private double[] referenceVector = new double[3];
    private double[] pivotVector;


    public Circle( @JsonProperty("center")double[] center,
                   @JsonProperty("radius")double radius,
                   @JsonProperty("normal")double[] normal,
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
        this.center = center;
        this.radius = radius;
        this.normal = normal;
    }

    @Override
    public double intersect(Ray ray) {

        return getIntersectSolution(ray);

    }
    /*Used for calculating the intersection distance to the sphere give the ray
    * using pythagorean calculations*/
    private double getIntersectSolution(Ray ray) {
        MathUtils.normalize(normal);

        d = -(MathUtils.dotProduct(normal, center));
        initializeVector();
        pivotVector = MathUtils.crossProduct(normal, referenceVector);

        MathUtils.normalize(pivotVector);

        double distance = intersectWithPlane(ray);

        return returnResult(ray, distance);
    }

    private double returnResult(Ray ray, double distance) {
        if (distance != Double.POSITIVE_INFINITY && distance > MathUtils.ZERO) {
            return intersectWithinRadius(ray, distance);
        }

        return Double.POSITIVE_INFINITY;
    }

    private double intersectWithPlane(Ray ray) {
        double[] raySource = ray.getPosition();
        double[] V = ray.getDirection();
        double distance = Double.POSITIVE_INFINITY;

        if (MathUtils.dotProduct(V, normal) != MathUtils.ZERO) {
            distance = (-(MathUtils.dotProduct(raySource, normal) + d)) / MathUtils.dotProduct(V, normal);
        }

        if (distance <= 0)
            return Double.POSITIVE_INFINITY;
        return distance;
    }


    private double intersectWithinRadius(Ray ray, double distance) {
        ray.setMagnitude(distance);
        intersectionPoint = ray.getEndPoint();
        ray.setMagnitude(1);

        double distanceFromCenter = MathUtils.norm(MathUtils.calcPointsDiff(center, intersectionPoint));

        return returnDistance(distance, distanceFromCenter);
    }

    private double returnDistance(double distance, double distanceFromCenter) {
        if (distanceFromCenter <= radius) {
            return distance;
        }

        return Double.POSITIVE_INFINITY;
    }

    @Override
    public double[] getNormal(double[] point) throws Exception {
        return normal;
    }

    @Override
    public double[] getTexturePoints(double[] point) {
        double[] centerToPoint = MathUtils.calcPointsDiff(center, point);

        double u = MathUtils.norm(centerToPoint) / radius;

        MathUtils.normalize(centerToPoint);

        double q = MathUtils.dotProduct(pivotVector, centerToPoint);
        if (Math.abs(q) > MathUtils.UNIT) q = MathUtils.UNIT * Math.signum(q);

        double v = Math.acos(q);

        if (MathUtils.dotProduct(MathUtils.crossProduct(pivotVector, centerToPoint), normal) < 0) {
            v = (2 * Math.PI) - v;
        }
        v /= (2 * Math.PI);

        return new double[]{u, v};
    }


    private void initializeVector() {

        referenceVector[0] = 1F;
        referenceVector[1] = 0F;
        referenceVector[2] = 0F;

        if (normal[1] == 0 && normal[2] == 0) {
            referenceVector[0] = 0F;
            referenceVector[1] = 1F;
            referenceVector[2] = 0F;
        }
    }
}
