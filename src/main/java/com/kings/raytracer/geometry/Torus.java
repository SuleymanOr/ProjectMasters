package com.kings.raytracer.geometry;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kings.raytracer.auxiliary.Ray;
import com.kings.raytracer.utility.MathUtils;
import com.kings.raytracer.utility.Matrix;

public class Torus extends Figure{
    private double[] center;
    private double centralRadius;
    private double tubeRadius;
    private double centralRadiusSquare;
    private double tubeRadiusSquare;
    private double[] normal;

    public Torus(double[] center,
                 double centralRadius,
                 double tubeRadius,
                 @JsonProperty("diffuse") double[] diffuse,
                 @JsonProperty("reflectance") double reflectance,
                 @JsonProperty("surfaceType") String surfaceType,
                 @JsonProperty("ambient") double[] ambient,
                 @JsonProperty("shininess") double shininess,
                 @JsonProperty("emission") double[] emission,
                 @JsonProperty("checkersDiffuse1") double[] checkersDiffuse1,
                 @JsonProperty("checkersDiffuse2") double[] checkersDiffuse2,
                 @JsonProperty("specular") double[] specular){
        super(diffuse,reflectance,surfaceType, ambient, shininess, emission, checkersDiffuse1, checkersDiffuse2, specular);
        this.center = center;
        this.centralRadius = centralRadius;
        this.tubeRadius = tubeRadius;
        centralRadiusSquare = MathUtils.sqr(centralRadius);
        tubeRadiusSquare = MathUtils.sqr(tubeRadius);
    }

    // quatric polynomial coefficients
    private double a4, a3, a2, a1, a0;
    private double alpha, beta, gamma;

    @Override
    public double[] getNormal(double[] point) throws Exception {
        double[] normal = {0, 0, 0};

        double innerComponent = MathUtils.sqr(point[0]) +
                MathUtils.sqr(point[1]) +
                MathUtils.sqr(point[2]) - tubeRadiusSquare - centralRadiusSquare;

        normal[0] = 4 * point[0] * innerComponent;
        normal[1] = 4 * point[1] * innerComponent;
        normal[2] = 4 * point[2] * innerComponent + (8 * centralRadiusSquare * MathUtils.sqr(point[2]));

        // Create the normal in matrix form


        Matrix normalMatrix = new Matrix(4, 1);
        normalMatrix.setValue(0, 0, normal[0]);
        normalMatrix.setValue(1, 0, normal[1]);
        normalMatrix.setValue(2, 0, normal[2]);
        normalMatrix.setValue(3, 0, 1);

        // Create the translation matrix

        Matrix M = Matrix.generateIdentity(4, 4);
        M.setValue(0, 3, center[0]);
        M.setValue(1, 3, center[1]);
        M.setValue(2, 3, center[2]);

        // Translate the normal

        Matrix Mnormal = M.times(normalMatrix);

        // Extract it from the matrix form

        double[] translatedNormal = {Mnormal.getValue(0, 0), Mnormal.getValue(1, 0), Mnormal.getValue(2, 0)};

        MathUtils.normalize(translatedNormal);
        return translatedNormal;
    }

    @Override
    public double[] getTexturePoints(double[] point) {

        double[] referenceVector = {1, 0, 0};
        double[] pointOnRing = point.clone();
        MathUtils.addVectorAndMultiply(pointOnRing, MathUtils.oppositeVector(normal), tubeRadius);
        double[] vectorToRing = MathUtils.calcPointsDiff(center, pointOnRing);

        MathUtils.normalize(vectorToRing);

        double u = Math.acos(MathUtils.dotProduct(referenceVector, vectorToRing));
        if (MathUtils.dotProduct(MathUtils.crossProduct(referenceVector, vectorToRing), normal) < 0) {
            u = 2 * Math.PI - u;
        }

        u /= (2 * Math.PI);

        double[] fromRingToPoint = MathUtils.calcPointsDiff(pointOnRing, point);

        MathUtils.normalize(fromRingToPoint);

        double v = Math.acos(MathUtils.dotProduct(referenceVector, fromRingToPoint));
//	    if(MathUtils.dotProduct(MathUtils.crossProduct(referenceVector, fromRingToPoint), normal) < 0)
//	    {
//	    	v = 2 * Math.PI - v;
//	    }
        v /= (2 * Math.PI);


        return new double[]{u, v};
    }



    @Override
    public double intersect(Ray ray) {

        // Convert the ray position and direction to matrix style
        Matrix rayPosition = new Matrix(4, 1);
        Matrix rayDirection = new Matrix(4, 1);
        rayPosition.setValue(0, 0, ray.getPosition()[0]);
        rayPosition.setValue(1, 0, ray.getPosition()[1]);
        rayPosition.setValue(2, 0, ray.getPosition()[2]);
        rayPosition.setValue(3, 0, 1);
        rayDirection.setValue(0, 0, ray.getDirection()[0]);
        rayDirection.setValue(1, 0, ray.getDirection()[1]);
        rayDirection.setValue(2, 0, ray.getDirection()[2]);
        rayDirection.setValue(3, 0, 1);

        // Create the translation matrix
        Matrix M = Matrix.generateIdentity(4, 4);
        M.setValue(0, 3, -center[0]);
        M.setValue(1, 3, -center[1]);
        M.setValue(2, 3, -center[2]);

        // Translate the position and direction vectors
        Matrix MPosition = M.times(rayPosition);
        Matrix MDirection = M.times(rayDirection);

        // Extract them from the matrix form
        double[] translatedPosition = {MPosition.getValue(0, 0), MPosition.getValue(1, 0), MPosition.getValue(2, 0)};
        double[] translatedDirection = {MDirection.getValue(0, 0), MDirection.getValue(1, 0), MDirection.getValue(2, 0)};

        MathUtils.normalize(translatedDirection);

        // Reconstruct the ray after translation
        ray.setPosition(translatedPosition);
        ray.setDirection(translatedDirection);

        // Prepare parameters to work with for solving the polynomial
        double[] p = ray.getPosition();
        double[] d = ray.getDirection();
        alpha = MathUtils.dotProduct(d, d);
        beta = 2 * MathUtils.dotProduct(p, d);
        gamma = MathUtils.dotProduct(p, p) - tubeRadiusSquare - centralRadiusSquare;

        // Quatric polynomial coefficients
        a4 = MathUtils.sqr(alpha);
        a3 = 2 * alpha * beta;
        a2 = (MathUtils.sqr(beta)) + (2 * alpha * gamma) + (4 * centralRadiusSquare * MathUtils.sqr(d[2]));
        a1 = (2 * beta * gamma) + (8 * centralRadiusSquare * p[2] * d[2]);
        a0 = MathUtils.sqr(gamma) + (4 * centralRadiusSquare * MathUtils.sqr(p[2])) - (4 * centralRadiusSquare * tubeRadiusSquare);

        // Solve quatric
        double[] coefficients = {a0, a1, a2, a3, a4};
        double[] roots = MathUtils.SolveQuartic(coefficients);

        if (roots == null || roots.length == 0) return Double.POSITIVE_INFINITY;

        // Find the closest intersecting point
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < roots.length; i++) {
            if (roots[i] < min) {
                min = roots[i];
            }
        }

        return (min == Double.POSITIVE_INFINITY) ? Double.POSITIVE_INFINITY : min;
    }

}
