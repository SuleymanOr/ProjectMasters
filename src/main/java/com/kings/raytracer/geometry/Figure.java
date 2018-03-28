package com.kings.raytracer.geometry;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.kings.raytracer.auxiliary.Ray;


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
        @JsonSubTypes.Type(value = Circle.class, name = "Disc" ),
        @JsonSubTypes.Type(value = Triangle.class, name = "Triangle" ),
})
public abstract class Figure {

    private double[] specular;
    private double[] diffuse;
    private double[] ambient;
    private double[] emission;
    private double shininess;
    private double reflectance ;
    private double checkersSize ;
    private double[] checkersDiffuse1;
    private double[] checkersDiffuse2;
    private String surfaceType;


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
