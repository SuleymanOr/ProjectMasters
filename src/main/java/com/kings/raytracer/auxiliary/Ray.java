package com.kings.raytracer.auxiliary;

import com.kings.raytracer.utility.MathUtils;

import java.util.Objects;

public class Ray {

    private double[] position;
    private double[] direction;
    private double magnitude;

    public Ray(double[] position, double[] direction, double magnitude){
        this.position = position.clone();
        this.direction = direction.clone();
        this.magnitude = magnitude;
    }

    public void normalize() {
        double norm  = MathUtils.norm(direction);

        direction[0] = direction[0] / norm;
        direction[1] = direction[1] / norm;
        direction[2] = direction[2] / norm;

        magnitude = 1;
    }

    public double[] getEndPoint() {
        double[] endPoint = { position[0] + magnitude * direction[0],
                position[1] + magnitude * direction[1],
                position[2] + magnitude * direction[2] };

        return endPoint;
    }


    @Override
    public boolean equals(Object o){
        // self check
        if(this == o){ return true; } else
            // null check
            if(o == null){ return false;} else
                // type check and cast
                if(getClass() != o.getClass()){ return false; } else {
                    final Ray a = (Ray) o;
                    // field comparison
                    return Objects.equals(a, a);
                }
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
