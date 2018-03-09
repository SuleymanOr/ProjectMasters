package com.kings.raytracer.geometry;

import com.kings.raytracer.auxiliary.Ray;

/**
 * Abstract class from which all geometric primitives inherit
 */
public abstract class Figure {

    private double[] specular = {1.0F, 1.0F, 1.0F}; // from surface class, used for all shapes
    private double[] diffuse = {1F, 1F, 1F}; // default values
    private double[] ambient = {0.1F, 0.1F, 0.1F};
    private double[] emission = {0, 0, 0};
    private double shininess = 100.0F;
    private double reflectance = 0.0F;
    private double checkersSize = 0.1F;
    private double[] checkersDiffuse1 = {1.0F, 1.0F, 1.0F};
    private double[] checkersDiffuse2 = {0.1F, 0.1F, 0.1F};
    private String surfaceType = "Normal";

    /**
     * A generic intersection algorithm which returns the distance between the ray and the
     * implementing primitive.  Returns Double.POSITIVE_INFINITY if there is no intersection.
     *
     * @param ray
     * @return
     */
    abstract public double intersect(Ray ray);

    public String getSurfaceType() {
        return surfaceType;
    }

    public void setSurfaceType(String surfaceType) {
        this.surfaceType = surfaceType;
    }

    public double getReflectance() {
        return reflectance;
    }

    public void setReflectance(double reflectance) {
        this.reflectance = reflectance;
    }

    public double[] getDiffuse() {
        return diffuse;
    }

    public void setDiffuse(double[] diffuse) {
        this.diffuse = diffuse;
    }

    public double[] getAmbient() {
        return ambient;
    }

    public void setAmbient(double[] ambient) {
        this.ambient = ambient;
    }

    public double[] getEmission() {
        return emission;
    }

    public void setEmission(double[] emission) {
        this.emission = emission;
    }

    public double getShininess() {
        return shininess;
    }

    public void setShininess(double shininess) {
        this.shininess = shininess;
    }

    public double[] getSpecular() {
        return specular;
    }

    public void setSpecular(double[] specular) {
        this.specular = specular;
    }

    // Return a normal vector for the given point
    public abstract double[] getNormal(double[] point) throws Exception;

    public double[] getCheckersColor(double[] point2D) {
        double checkersX = Math.abs(Math.floor(point2D[0] / checkersSize) % 2);
        double checkersY = Math.abs(Math.floor(point2D[1] / checkersSize) % 2);

        if (checkersX == 0 && checkersY == 0) return checkersDiffuse2;
        if (checkersX == 0 && checkersY == 1) return checkersDiffuse1;
        if (checkersX == 1 && checkersY == 0) return checkersDiffuse1;
        if (checkersX == 1 && checkersY == 1) return checkersDiffuse2;

        return null;
    }

    public abstract double[] getTextureCoords(double[] point);

    public double[] getColorAt(double[] point) {
        if(surfaceType.equals("Checkers"))
            return getCheckersColor(getTextureCoords(point));
        else if(surfaceType.equals("Normal"))
                return getDiffuse();

        return getDiffuse();
    }
}
