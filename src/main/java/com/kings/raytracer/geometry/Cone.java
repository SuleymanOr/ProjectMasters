package com.kings.raytracer.geometry;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kings.raytracer.auxiliary.Ray;
import com.kings.raytracer.utility.MathUtils;

public class Cone extends Figure  {

    private  double[] start;

    private  double[] direction;

    private  double angle;

    private  double height;


    public Cone( @JsonProperty("start")double[] start,
                 @JsonProperty("direction")double[] direction,
                 @JsonProperty("angle")double angle,
                 @JsonProperty("height")double height,
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
        this.start = new double[]{0, 0, 0.5};
        this.direction = MathUtils.normalizeReturn(new double[]{0, -0.4, -1});
        this.angle = 0.5;
        this.height = 0.8;
    }

    @Override
    public double[] getNormal(double[] point) {
        double[] topToPointDirection = MathUtils.normalizeReturn(MathUtils.calcPointsDiff(start,point));
        double[] c =MathUtils.crossProduct(direction,topToPointDirection);

        return MathUtils.normalizeReturn(MathUtils.crossProduct(c,topToPointDirection));
    }

    @Override
    public double[] getTexturePoints(double[] point) {
        return new double[0];
    }

    private boolean checkRoot(Ray ray, double root) {
        if (root >= MathUtils.GEOMETRY_THRESHOLD) {

           double[] m = MathUtils.multiplyVector(ray.getDirection(),root);
            double[] v = MathUtils.addVectorReturn(ray.getPosition(),m);

            return isPointOnCone(v);
        }
        return false;
    }

    private boolean isPointOnCone(double[] point) {
        double bottomCheck[] = MathUtils.calcPointsDiff(start,point);
        double l = Math.sqrt(
                MathUtils.sqr(bottomCheck[0]) +
                        MathUtils.sqr(bottomCheck[1]) +
                        MathUtils.sqr(bottomCheck[2]));

        boolean b = l * Math.cos(angle) <= height;


        double[] p =MathUtils.calcPointsDiff (start,point);
        boolean topCheck =   MathUtils.dotProduct( p,direction) >= 0  ;
        return b && topCheck;
    }

    @Override
    public double intersect(Ray ray) {

        double[] rayToTop = MathUtils.calcPointsDiff(start,ray.getPosition());

        double AxR =  MathUtils.dotProduct(direction,ray.getDirection());
        double AxBR = MathUtils.dotProduct(direction,rayToTop);
        double cosSquare = Math.pow(Math.cos(angle), 2);

        double paramA = AxR * AxR - cosSquare *  MathUtils.dotProduct(ray.getDirection(),ray.getDirection());
        double paramB = 2 * (AxR * AxBR - cosSquare * MathUtils.dotProduct(ray.getDirection(),rayToTop));
        double paramC = AxBR * AxBR - cosSquare *  MathUtils.dotProduct(rayToTop,rayToTop);

        double roots[] = MathUtils.solveQuadraticEquation(paramA, paramB, paramC);

        if (roots != null) {
            if (roots.length > 1) {
                double minRoot = Math.min(roots[0], roots[1]);
                double maxRoot = Math.max(roots[0], roots[1]);

                if (checkRoot(ray, minRoot)) {
                    return minRoot;
                } else if (checkRoot(ray, maxRoot)) {
                    return maxRoot;
                }
            } else {
                if (checkRoot(ray, roots[0])) {
                    return roots[0];
                }
                else if(checkRoot(ray, roots[1])){
                        return roots[1];
                    }
                else
                    return Double.POSITIVE_INFINITY;
                }
            }


        return Double.NaN;
    }


    public double[] getDirection() {
        return direction;
    }

    public void setDirection(double[] direction) {
        this.direction = direction;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}