package com.kings.raytracer.light;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kings.raytracer.utility.MathUtils;

public class LightDirected implements Light {

    private double[] position;
    private double[] direction;
    private double[] oppositeDirection;
    private double[] color = {1,1,1};

    @JsonCreator
    public LightDirected(@JsonProperty("direction")double[] direction, @JsonProperty("color")double[] color) {
        super();
        position = new double[] { Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY };
        this.direction = MathUtils.normalizeReturn(direction);
        this.color = color;
        this.oppositeDirection = MathUtils.oppositeVector(this.direction);
    }

    @Override
    public double[] getAmountOfLight(double[] point) {
        return getColor(); // constant light, regardless of distance to target
    }

    @Override
    public double[] getPosition() {
        return position;
    }

    @Override
    public void setPosition(double[] position) {
        this.position = position;
    }

    @Override
    public double[] getColor() {
        return color;
    }

    @Override
    public void setColor(double[] color) {
        this.color = color;
    }

    @Override
    public double[] getVectorToLight(double[] pointOfIntersection) {
        return oppositeDirection;
    }
}

