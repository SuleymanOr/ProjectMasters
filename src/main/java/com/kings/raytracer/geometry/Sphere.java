package com.kings.raytracer.geometry;

import com.kings.raytracer.auxiliary.Ray;
import com.kings.raytracer.utility.MathUtils;

public class Sphere extends Figure {

    private double[] center;
    private double radius;

    public Sphere(double[] center, double radius, double[] color, double[] specular, double reflection, double shininess, double[] ambient,  String surfaceType) {
        this.center = center;
        this.radius = radius;
        this.setDiffuse(color);
        this.setSpecular(specular);
        this.setReflectance(reflection);
        this.setShininess(shininess);
        this.setAmbient(ambient);
        this.setSurfaceType(surfaceType);
    }


    @Override
    public double intersect(Ray ray) {
        return intersectSolution(ray);
    }

    private double intersectSolution(Ray ray) {

        double[] vectorDifference = MathUtils.calcPointsDiff(ray.getPosition(), center);
        double[] vectorDirection = ray.getDirection();

        double projection = MathUtils.dotProduct(vectorDifference, vectorDirection);

        if (projection < 0) {
            return Double.POSITIVE_INFINITY;
        }

        double lightSquare = MathUtils.dotProduct(vectorDifference, vectorDifference);

        double distanceSquare = lightSquare - MathUtils.sqr(projection);
        double radiusSquare = MathUtils.sqr(radius);

        if (distanceSquare > radiusSquare) {
            return Double.POSITIVE_INFINITY;
        }else{
            return getIntersectionDistance(vectorDifference, projection, lightSquare, distanceSquare, radiusSquare);
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
        double[] distanceToCenter = MathUtils.calcPointsDiff(center, point);

        double pointY = getPointY(distanceToCenter[2]);

        double pointX = getPointX(distanceToCenter[0] / (radius * Math.sin(pointY)));

        if (distanceToCenter[1] < MathUtils.ZERO)
            pointX = -pointX;
        if (distanceToCenter[2] < MathUtils.ZERO)
            pointY = pointY + Math.PI;

        pointX = (pointX / (2 * Math.PI));
        pointY = (pointY / Math.PI);

        pointX = finalPointX(pointX);

        return new double[]{pointX, pointY};
    }

    private double getIntersectionDistance(double[] vectorDifference, double projection, double lightSquare, double distanceSquare, double radiusSquare) {
        double offset = Math.sqrt(radiusSquare - distanceSquare);
        double threshold= MathUtils.dotProduct(vectorDifference, vectorDifference);

        if ( threshold < lightSquare) {
            return projection + offset;
        } else {
            return projection - offset;
        }
    }

    private double finalPointX(double pointX) {
        if (pointX > 1) pointX -= 1;
        if (pointX < 0) pointX += 1;
        return pointX;
    }

    private double getPointX(double pointX1) {
        double pointX = pointX1;

        if (Math.abs(pointX) > MathUtils.UNIT) {
            pointX = Math.signum(pointX);
        }
        pointX = Math.acos(pointX);
        return pointX;
    }

    private double getPointY(double pointY1) {
        double pointY = pointY1 / radius;

        if (Math.abs(pointY) > MathUtils.UNIT) {
            pointY -= MathUtils.UNIT * Math.signum(pointY);
        }
        pointY = Math.acos(pointY);
        return pointY;
    }

    public double[] getCenter() {
        return center;
    }

    public void setCenter(double[] center) {
        this.center = center;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}

