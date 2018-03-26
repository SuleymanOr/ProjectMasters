package com.kings.raytracer.light;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kings.raytracer.utility.MathUtils;

public class Light {

    private double[] position;
    private double[] direction;
    private double[] oppositeDirection;
    private double[] color = {1,1,1};

    @JsonCreator
    public Light(@JsonProperty("direction")double[] direction, @JsonProperty("color")double[] color) {
        position = new double[] { Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY };
        this.direction = MathUtils.normalizeReturn(direction);
        this.color = color;
        this.oppositeDirection = MathUtils.oppositeVector(this.direction);
    }

    public double[] getAmountOfLight(double[] point) {
        return getColor();          // constant light, regardless of distance
    }

    public double[] getPosition() {
        return position;
    }

    public void setPosition(double[] position) {
        this.position = position;
    }

    public double[] getColor() {
        return color;
    }

    public void setColor(double[] color) {
        this.color = color;
    }

    public double[] getVectorToLight(double[] pointOfIntersection) {
        return oppositeDirection;
    }
}

