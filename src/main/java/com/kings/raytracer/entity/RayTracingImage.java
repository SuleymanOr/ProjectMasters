package com.kings.raytracer.entity;

public class RayTracingImage {
    private String shape;
    private String size;
    private String volume;

    public RayTracingImage(){}

    public RayTracingImage(String shape, String size, String volume) {
        this.shape = shape;
        this.size = size;
        this.volume = volume;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }
}
