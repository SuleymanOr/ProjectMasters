package com.kings.raytracer.auxiliary;

import com.kings.raytracer.utility.MathUtils;

public class Ray {

    private double[] position;
    private double[] direction;
    private double magnitude;

    public Ray(double[] position, double[] direction, double magnitude) throws Exception {
        if (position.length != 3 || direction.length != 3)
            throw new Exception ("position and direction vectors must be of length 3.");

        this.position = position.clone();
        this.direction = direction.clone();
        this.magnitude = magnitude;
    }

    // Normalizes the vector
    public void normalize() {
        double norm  = MathUtils.norm(direction);

        direction[0] = direction[0] / norm;
        direction[1] = direction[1] / norm;
        direction[2] = direction[2] / norm;

        magnitude = 1;
    }

    // Returns the end of the vector as a point in 3D space
    public double[] getEndPoint() {
        double[] endPoint = { position[0] + magnitude * direction[0],
                position[1] + magnitude * direction[1],
                position[2] + magnitude * direction[2] };

        return endPoint;
    }

    public double[] getPosition() {
        return position;
    }

    public void setPosition(double[] position) {
        this.position = position;
    }

    public double[] getDirection() {
        return direction;
    }

    public void setDirection(double[] direction) {
        this.direction = direction;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

}
