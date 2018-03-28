package com.kings.raytracer.geometry;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kings.raytracer.auxiliary.Ray;
import com.kings.raytracer.utility.MathUtils;

public class Cylinder extends Figure {

    private double[] start = new double[3];
    private double[] center = null;
    private double[] end = new double[3];
    private double[] direction = null;
    private double length;
    private double radius;

    // Fields for calculation optimizations
    private double radiusSquare;
    private double[] AB;
    private double ABdotAB;
    private double[] referenceVector;
    private double[] pivotVector;

    public Cylinder(@JsonProperty("center")double[] center,
                    @JsonProperty("direction")double[] direction,
                    @JsonProperty("length")double length,
                    @JsonProperty("radius")double radius,
                    @JsonProperty("diffuse")double[] diffuse,
                    @JsonProperty("reflectance")double reflectance,
                    @JsonProperty("surfaceType")String surfaceType,
                    @JsonProperty("ambient")double[] ambient,
                    @JsonProperty("shininess")double shininess,
                    @JsonProperty("emission")double[] emission,
                    @JsonProperty("checkersDiffuse1")double[] checkersDiffuse1,
                    @JsonProperty("checkersDiffuse2")double[] checkersDiffuse2,
                    @JsonProperty("specular")double[] specular) {
        super(diffuse,reflectance,surfaceType, ambient, shininess, emission, checkersDiffuse1, checkersDiffuse2, specular);
        this.center = center;
        this.direction = direction;
        this.length = length;
        this.radius = radius;
    }

    @Override
    public double[] getNormal(double[] point) throws Exception {

        // Formulas according to http://answers.yahoo.com/question/index?qid=20080218071458AAYz1s1
        double[] AP, center;

        AP = MathUtils.calcPointsDiff(start, point);
        double t = MathUtils.dotProduct(AB, AP) / ABdotAB;
        center = start.clone();
        MathUtils.addVectorAndMultiply(center, AB, t);

        double[] normal = MathUtils.calcPointsDiff(center, point);
        MathUtils.normalize(normal);

        return normal;
    }


    /*
     * Calculate the intersect to the cylinder.
     * (non-Javadoc)
     * @see Primitive#intersect(Ray)
     */
    @Override
    public double intersect(Ray ray) {

        return getIntersectionSolution(ray);
    }

    private double getIntersectionSolution(Ray ray) {
        double[] AO, AOxAB, VxAB;
        double a, b, c;


        initializeReferenceVector();
        MathUtils.normalize(direction);
        pivotVector = MathUtils.crossProduct(direction, referenceVector);
        MathUtils.normalize(pivotVector);


        start[0] = center[0] - (direction[0] * length/2);
        start[1] = center[1] - (direction[1] * length/2);
        start[2] = center[2] - (direction[2] * length/2);

        end[0] = center[0] + (direction[0] * length/2);
        end[1] = center[1] + (direction[1] * length/2);
        end[2] = center[2] + (direction[2] * length/2);


        radiusSquare = radius * radius;
        AB = MathUtils.calcPointsDiff(start, end);
        ABdotAB = MathUtils.dotProduct(AB, AB);


        AO = MathUtils.calcPointsDiff(start, ray.getPosition());
        AOxAB = MathUtils.crossProduct(AO, direction);
        VxAB = MathUtils.crossProduct(ray.getDirection(), direction);


        a = MathUtils.dotProduct(VxAB, VxAB);
        b = 2 * MathUtils.dotProduct(VxAB, AOxAB);
        c = MathUtils.dotProduct(AOxAB, AOxAB) - radiusSquare;


        double[] roots = MathUtils.solveQuadraticEquation(a, b, c);
        return returnIntersectionDistance(ray, roots);
    }

    private double returnIntersectionDistance(Ray ray, double[] roots) {
        double distance;

        if (roots[0] == Double.POSITIVE_INFINITY) {
            distance = Double.POSITIVE_INFINITY;
        } else if (roots[0] <= 0 && roots[1] <= 0) {
            distance = Double.POSITIVE_INFINITY;
        }

        else if (roots[0] >= 0 && roots[1] >= 0) {
            if (isPointOnCylinder(roots[0], ray)) {
                if (isPointOnCylinder(roots[1], ray)) {
                    distance = Math.min(roots[0], roots[1]);
                } else {
                    distance = roots[0];
                }
            } else if (isPointOnCylinder(roots[1], ray)) {
                distance = roots[1];
            } else {
                distance = Double.POSITIVE_INFINITY;
            }
        } else {
            distance = Math.max(roots[0], roots[1]);
        }

        return distance;
    }


    private boolean isPointOnCylinder(double root, Ray ray) {
        ray.setMagnitude(root);
        double[] AP = MathUtils.calcPointsDiff(start, ray.getEndPoint());
        double t = MathUtils.dotProduct(direction, AP);

        if (t > length || t < 0)
            return false;
        else{
            ray.setMagnitude(1);
            return true;
        }

    }

    private void initializeReferenceVector() {

        referenceVector = new double[]{1F, 0F, 0F};

        if (MathUtils.dotProduct(referenceVector, direction) > 0.9)
            referenceVector = new double[]{0F, 1F, 0F};

        MathUtils.normalize(referenceVector);
    }

    @Override
    public double[] getTexturePoints(double[] point) {
        try {
            double pointStartDiff = MathUtils.norm(MathUtils.calcPointsDiff(point, start));
            double dist = Math.sqrt(Math.abs(MathUtils.sqr(pointStartDiff) - MathUtils.sqr(radius)));

            Ray startToCenter = new Ray(start, direction, dist);
            double[] pointToCenter = MathUtils.calcPointsDiff(point, startToCenter.getEndPoint());
            MathUtils.normalize(pointToCenter);

            double u = dist / length;
            double q = MathUtils.dotProduct(pointToCenter, referenceVector);
            if (Math.abs(q) > 1) q = 1 * Math.signum(q);

            double v = Math.acos(q);
            double[] orthoToPointToCenter = MathUtils.crossProduct(pointToCenter, referenceVector);
            MathUtils.normalize(orthoToPointToCenter);

            if (MathUtils.dotProduct(orthoToPointToCenter, direction) < 0) {
                v = (2 * Math.PI) - v;
            }

            v = v / (2 * Math.PI);

            return new double[]{u, v};
        } catch (Exception e) {
            e.printStackTrace();
            return new double[]{0, 0};
        }
    }
}