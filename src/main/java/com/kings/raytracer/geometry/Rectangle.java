package com.kings.raytracer.geometry;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kings.raytracer.auxiliary.Ray;
import com.kings.raytracer.utility.MathUtils;

public class Rectangle extends Figure {

    private double[] point0, point1, point2, point3;
    private double[] normal = null;
    private double[] intersectionPoint = null;
    private double[] AB, AC;

    private double projection;
    private double ABdotAB, ACdotAC;
    private double ABnorm;
    private double ACnorm;


    public Rectangle(@JsonProperty("point0")double[] point0,
                     @JsonProperty("point1")double[] point1,
                     @JsonProperty("point2") double[] point2,
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
        setAdditionalValues();
    }


    @Override
    public double intersect(Ray ray) {
        return intersectSolution(ray);

    }

    private double intersectSolution(Ray ray) {
        if (distanceToPlaneIntersection(ray) != Double.POSITIVE_INFINITY &&
                distanceToPlaneIntersection(ray) != Double.NEGATIVE_INFINITY) {
            return intersectCentre(ray, distanceToPlaneIntersection(ray));
        }else{
            return Double.POSITIVE_INFINITY;
        }
    }

    @Override
    public double[] getNormal(double[] point) {
        return normal;
    }

    @Override
    public double[] getTexturePoints(double[] point) {

        double[] AP;

        AP = MathUtils.calcPointsDiff(point0, point);
        double q = 1 / MathUtils.norm(MathUtils.calcPointsDiff(point0, point1));

        double u = MathUtils.dotProduct(AB, AP) / ABdotAB;
        double v = MathUtils.dotProduct(AC, AP) / ACdotAC;

        u /= ABnorm * q;
        v /= ACnorm * q;

        return new double[]{u, v};
    }

    private double distanceToPlaneIntersection(Ray ray) {
        double distanceToPlane = 0;
        if (MathUtils.dotProduct(ray.getDirection(), normal) != 0) {
            distanceToPlane = (-(MathUtils.dotProduct(ray.getPosition(), normal) + projection)) / MathUtils.dotProduct(ray.getDirection(), normal);
        }
        return getDistance(distanceToPlane);
    }

    private double getDistance(double distance) {
        if (distance <= 0)
            return Double.POSITIVE_INFINITY;
        return distance;
    }


    private double intersectCentre(Ray ray, double distance) {

        double[] d0, d1, d2;
        double dot00, dot01, dot02, dot11, dot12;
        double denominator, a, b;

        ray.setMagnitude(distance);
        intersectionPoint = ray.getEndPoint();

        d0 = MathUtils.calcPointsDiff(point0, point2);
        d1 = MathUtils.calcPointsDiff(point0, point1);
        d2 = MathUtils.calcPointsDiff(point0, intersectionPoint);

        dot00 = MathUtils.dotProduct(d0, d0);
        dot01 = MathUtils.dotProduct(d0, d1);
        dot02 = MathUtils.dotProduct(d0, d2);
        dot11 = MathUtils.dotProduct(d1, d1);
        dot12 = MathUtils.dotProduct(d1, d2);

        denominator = MathUtils.UNIT / (dot00 * dot11 - dot01 * dot01);
        a = (dot11 * dot02 - dot01 * dot12) * denominator;
        b = (dot00 * dot12 - dot01 * dot02) * denominator;

        return getIntersect(distance, a, b);

    }

    private double getIntersect(double distance, double a, double b) {
        if ((a > 0) && (b > 0) && (a < 1) && (b < 1)) {
            return distance;
        }else {
            return Double.POSITIVE_INFINITY;
        }
    }

    public double[] getNormal() {
        return normal;
    }

    public void setAdditionalValues() {

        point3 = getFourthPoint(point0, point1, point2);

        normal = MathUtils.crossProduct(MathUtils.calcPointsDiff(point0, point1), MathUtils.calcPointsDiff(point0, point2));
        MathUtils.normalize(normal);
        projection = -(MathUtils.dotProduct(normal, point0));

        AB = MathUtils.calcPointsDiff(point0, point1);
        ABdotAB = MathUtils.dotProduct(AB, AB);
        AC = MathUtils.calcPointsDiff(point0, point2);
        ACdotAC = MathUtils.dotProduct(AC, AC);
        ABnorm = MathUtils.norm(AB);
        ACnorm = MathUtils.norm(AC);
    }

    private double[] getFourthPoint(double[] p0, double[] p1, double[] p2) {
        double[] finalPoint = {p1[0] + (p2[0] - p0[0]), p1[1] + (p2[1] - p0[1]), p1[2] + (p2[2] - p0[2])};
        return finalPoint;
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

    public double[] getPoint3() {
        return point3;
    }

    public void setPoint3(double[] point3) {
        this.point3 = point3;
    }

    public void setNormal(double[] normal) {
        this.normal = normal;
    }
}
