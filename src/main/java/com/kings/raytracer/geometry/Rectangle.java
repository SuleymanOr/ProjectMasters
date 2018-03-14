package com.kings.raytracer.geometry;

import com.kings.raytracer.auxiliary.Ray;
import com.kings.raytracer.utility.MathUtils;

public class Rectangle extends Figure {

    private double[] p0, p1, p2, p3;
    private double[] normal = null;
    private double[] intersectionPoint = null;
    private double d;
    private double[] AB, AC;
    private double ABdotAB, ACdotAC;
    private double ABnorm;
    private double ACnorm;


    public Rectangle(double[] p0, double[] p1, double[] p2, double[] color, String surfaceType) {
        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
        this.setAmbient(new double[]{0.1F,0.1F,0.1F});
        this.setSpecular(new double[]{0.6F,0.6F,0.8F});
        this.setShininess(120);
        this.setDiffuse(color);
        this.setSurfaceType("Checkers");
        setUpRectangle();
    }


    @Override
    public double intersect(Ray ray) {
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

        AP = MathUtils.calcPointsDiff(p0, point);
        double q = 1 / MathUtils.norm(MathUtils.calcPointsDiff(p0, p1));

        double u = MathUtils.dotProduct(AB, AP) / ABdotAB;
        double v = MathUtils.dotProduct(AC, AP) / ACdotAC;

        u /= ABnorm * q;
        v /= ACnorm * q;

        return new double[]{u, v};
    }

    private double distanceToPlaneIntersection(Ray ray) {
        double distanceToPlane = 0;
        if (MathUtils.dotProduct(ray.getDirection(), normal) != 0) {
            distanceToPlane = (-(MathUtils.dotProduct(ray.getPosition(), normal) + d)) / MathUtils.dotProduct(ray.getDirection(), normal);
        }
        return getDistance(distanceToPlane);
    }

    private double getDistance(double distance) {
        if (distance <= 0)
            return Double.POSITIVE_INFINITY;
        return distance;
    }


    private double intersectCentre(Ray ray, double distance) {

        double[] v0, v1, v2;
        double dot00, dot01, dot02, dot11, dot12;
        double denominator, u, v;

        ray.setMagnitude(distance);
        intersectionPoint = ray.getEndPoint();

        v0 = MathUtils.calcPointsDiff(p0, p2);
        v1 = MathUtils.calcPointsDiff(p0, p1);
        v2 = MathUtils.calcPointsDiff(p0, intersectionPoint);

        dot00 = MathUtils.dotProduct(v0, v0);
        dot01 = MathUtils.dotProduct(v0, v1);
        dot02 = MathUtils.dotProduct(v0, v2);
        dot11 = MathUtils.dotProduct(v1, v1);
        dot12 = MathUtils.dotProduct(v1, v2);

        denominator = 1 / (dot00 * dot11 - dot01 * dot01);
        u = (dot11 * dot02 - dot01 * dot12) * denominator;
        v = (dot00 * dot12 - dot01 * dot02) * denominator;

        if ((u > 0) && (v > 0) && (u < 1) && (v < 1)) {
            return distance;
        }else {
            return Double.POSITIVE_INFINITY;
        }


    }
    public double[] getNormal() {
        return normal;
    }

    public void setUpRectangle() {

        p3 = calcFourthPoint(p0, p1, p2);

        normal = MathUtils.crossProduct(MathUtils.calcPointsDiff(p0, p1), MathUtils.calcPointsDiff(p0, p2));
        MathUtils.normalize(normal);
        d = -(MathUtils.dotProduct(normal, p0));

        AB = MathUtils.calcPointsDiff(p0, p1);
        ABdotAB = MathUtils.dotProduct(AB, AB);
        AC = MathUtils.calcPointsDiff(p0, p2);
        ACdotAC = MathUtils.dotProduct(AC, AC);
        ABnorm = MathUtils.norm(AB);
        ACnorm = MathUtils.norm(AC);
    }

    private double[] calcFourthPoint(double[] p0, double[] p1, double[] p2) {
        double[] p3 = {p1[0] + (p2[0] - p0[0]), p1[1] + (p2[1] - p0[1]), p1[2] + (p2[2] - p0[2])};
        return p3;
    }


}
