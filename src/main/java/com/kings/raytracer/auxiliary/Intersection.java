package com.kings.raytracer.auxiliary;

import com.kings.raytracer.geometry.Figure;

import java.util.Objects;
/*Helper class used in storing the intersection*/
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

    @Override
    public boolean equals(Object o){
        // self check
        if(this == o){ return true; } else
            // null check
            if(o == null){ return false;} else
                // type check and cast
                if(getClass() != o.getClass()){ return false; } else {
                    final Intersection a = (Intersection) o;
                    // field comparison
                    return Objects.equals(a, a);
                }
    }


}

