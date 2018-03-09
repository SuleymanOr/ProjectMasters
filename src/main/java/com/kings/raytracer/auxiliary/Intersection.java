package com.kings.raytracer.auxiliary;

import com.kings.raytracer.geometry.Figure;

public class Intersection {

    private Figure figure;
    private double distance;

    public Intersection(double distance, Figure figure) {
        this.figure = figure;
        this.distance = distance;
    }

    public Figure getFigure() {
        return figure;
    }

    public double getDistance() {
        return distance;
    }

}

