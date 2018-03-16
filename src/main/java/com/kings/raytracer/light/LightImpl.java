package com.kings.raytracer.light;

import com.kings.raytracer.utility.MathUtils;

public class LightImpl implements Light {

    private double[] position;
    private double[] direction;
    private double[] oppositeDirection;
    private double[] color;

    public LightImpl(double[] direction, double[] color) {
        position = new double[] { Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY };
        this.direction = MathUtils.normalizeReturn(direction);
        this.color = color;
        this.oppositeDirection = MathUtils.oppositeVector(this.direction);
    }

    @Override
    public double[] getAmountOfLight(double[] point) {
        return getColor();
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

