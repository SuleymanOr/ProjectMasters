package com.kings.raytracer.geometry;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.kings.raytracer.auxiliary.Ray;

/**
 * Abstract class from which all geometric primitives inherit
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Sphere.class, name = "Sphere" ),
        @JsonSubTypes.Type(value = Cylinder.class, name = "Cylinder" ),
        @JsonSubTypes.Type(value = Cone.class, name = "Cone" ),
        @JsonSubTypes.Type(value = Rectangle.class, name = "Plane" ),
        @JsonSubTypes.Type(value = Cube.class, name = "Cube" ),
        @JsonSubTypes.Type(value = Torus.class, name = "Torus" ),
})
public abstract class Figure {

//    TODO: Delete default values

    private double[] specular = {1.0F, 1.0F, 1.0F};
    private double[] diffuse = {1F, 1F, 1F}; // default values
    private double[] ambient = {0.1F, 0.1F, 0.1F};
    private double[] emission = {0, 0, 0};
    private double shininess = 100.0F;
    private double reflectance = 0.0F;
    private double checkersSize = 0.1F;
    private double[] checkersDiffuse1 = {1.0F, 1.0F, 1.0F};
    private double[] checkersDiffuse2 = {0.1F, 0.1F, 0.1F};
    private String surfaceType ;                // type of the surface, either normal or checkers

    public Figure(double[] diffuse, double reflectance,
                  String surfaceType, double[] ambient,
                  double shininess, double[] emission,
                  double[] checkersDiffuse1,
                  double[] checkersDiffuse2,
                  double[] specular) {
        this.diffuse = diffuse;
        this.reflectance = reflectance;
        this.surfaceType = surfaceType;
        this.ambient = ambient;
        this.shininess = shininess;
        this.emission = emission;
        this.checkersDiffuse1 = checkersDiffuse1;
        this.checkersDiffuse2 = checkersDiffuse2;
        this.specular = specular;

    }

    // method returning the distance between the ray and the shape
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

    public abstract double[] getTexturePoints(double[] point);

    public double[] getColorAt(double[] point) {
        if(surfaceType.equals("Checkers"))
            return getCheckersColor(getTexturePoints(point));
        else if(surfaceType.equals("Normal"))
                return getDiffuse();

        return getDiffuse();
    }
}
