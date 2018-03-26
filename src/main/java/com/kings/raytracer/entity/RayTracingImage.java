package com.kings.raytracer.entity;

import com.kings.raytracer.geometry.Figure;

//TODO: Delete this class

public class RayTracingImage {
    private double radius;

    public RayTracingImage() {
    }

    public RayTracingImage(double radius) {
        this.radius = radius;
    }

    public Figure parseFigure(String figure) throws Exception {
        switch (figure) {
            case "Sphere":
                //return new Sphere();

            case "Cylinder":
                //return new Cylinder();

            default: throw new Exception("Provided figure cannot be rendered") ;
        }
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

}
