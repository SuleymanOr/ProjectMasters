package com.kings.raytracer.entity;


import com.kings.raytracer.auxiliary.Camera;
import com.kings.raytracer.geometry.Figure;
import com.kings.raytracer.light.Light;

import java.util.ArrayList;
import java.util.List;

// A POJO class populated by Spring using the information passed on in JSON format

public class RayTracingImage {

    ////////  Figure values ////////
    private List<Figure> figures = new ArrayList<>();
    private List<Light> lights = new ArrayList<>();

    ////////  Scene values  ////////
    private double[] backgroundColor;
    private double[] ambientLight;
    private int superSampleValue;
    private int screenWidth;
    private int screenHeight;

    ////////  Camera values ///////
    private Camera camera;

    ////////////////////////////////  FIGURE VALUES  ////////////////////////////////////////

    public List<Figure> getFigures() {
        return figures;
    }

    public void setFigures(List<Figure> figures) {
        this.figures = figures;
    }

    public List<Light> getLights() {
        return lights;
    }

    public void setLights(List<Light> lights) {
        this.lights = lights;
    }


    ////////////////////////////////  SCENE VALUES  ////////////////////////////////////////

    public double[] getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(double[] backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public double[] getAmbientLight() {
        return ambientLight;
    }

    public void setAmbientLight(double[] ambientLight) {
        this.ambientLight = ambientLight;
    }

    public int getSuperSampleValue() {
        return superSampleValue;
    }

    public void setSuperSampleValue(int superSampleValue) {
        this.superSampleValue = superSampleValue;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }


    /////////////////////////////  CAMERA VALUES  //////////////////////////////////////////


    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
}
