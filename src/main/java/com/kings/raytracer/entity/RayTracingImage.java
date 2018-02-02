package com.kings.raytracer.entity;

public class RayTracingImage {
   private final long id;
   private final String content;

    public RayTracingImage(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
