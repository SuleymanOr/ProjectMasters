package com.kings.raytracer.geometry;

import java.util.ArrayList;
import java.util.List;

public class ClientObject {

    private List<Figure> figures;


    public ClientObject() {
        this.figures = new ArrayList<>();
    }

    public ClientObject(List<Figure> figures) {
        this.figures = figures;
    }

    public List<Figure> getFigures() {
        return figures;
    }

    public void setFigures(List<Figure> figures) {
        this.figures = figures;
    }
}
