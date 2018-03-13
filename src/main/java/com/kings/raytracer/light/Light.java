package com.kings.raytracer.light;

public interface Light {
    double[] getAmountOfLight(double[] point);

    double[] getVectorToLight(double[] pointOfIntersection) throws Exception;

    double[] getPosition();

    void setPosition(double[] position);

    double[] getColor();

    void setColor(double[] color);
}
