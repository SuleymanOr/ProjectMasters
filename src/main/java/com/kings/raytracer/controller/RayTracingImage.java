package com.kings.raytracer.controller;


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
    private double[] eye;
    private double[] lookAt;
    private double[] upDirection;
    private double cameraScreenDist = 1;
    private double cameraScreenWidth = 2;

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

    public double[] getEye() {
        return eye;
    }

    public void setEye(double[] eye) {
        this.eye = eye;
    }

    public double[] getLookAt() {
        return lookAt;
    }

    public void setLookAt(double[] lookAt) {
        this.lookAt = lookAt;
    }

    public double[] getUpDirection() {
        return upDirection;
    }

    public void setUpDirection(double[] upDirection) {
        this.upDirection = upDirection;
    }

    public double getCameraScreenDist() {
        return cameraScreenDist;
    }

    public void setCameraScreenDist(double cameraScreenDist) {
        this.cameraScreenDist = cameraScreenDist;
    }

    public double getCameraScreenWidth() {
        return cameraScreenWidth;
    }

    public void setCameraScreenWidth(double cameraScreenWidth) {
        this.cameraScreenWidth = cameraScreenWidth;
    }

}
