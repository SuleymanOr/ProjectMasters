package com.kings.raytracer.utility;

import com.kings.raytracer.geometry.Figure;
import com.kings.raytracer.light.Light;

import java.util.List;

/*Helper class that creates the image environment with all the available entity*/
public class Scene {

    private List<Figure> figures;
    private List<Light> lights;

    private double[] backgroundColor;
    private double[] ambientLight;
    private int superSampleValue;

    private int textureWidth;
    private int textureHeight;
    private double[][][] backgroundTexture = null;

    private int imageWidth;
    private int imageHeight;

    public Scene(List<Figure> figures, List<Light> lights, double[] backgroundColor, double[] ambientLight, int superSampleValue, int imageWidth, int imageHeight) {
        this.figures = figures;
        this.lights = lights;
        this.backgroundColor = backgroundColor;
        this.ambientLight = ambientLight;
        this.superSampleValue = superSampleValue;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }
    public double[] getBackgroundAt(int x, int y) {
        if (backgroundTexture != null) {
            int textureX = (int) Math.round((double) x / imageWidth * textureWidth);
            int textureY = (int) Math.round((double) y / imageHeight * textureHeight);

            return backgroundTexture[textureY][textureX];
        } else {
            return backgroundColor;
        }

    }

    public List<Figure> getFigures() {
        return figures;
    }

    public void setFigures(List<Figure> listOfFigures) {
        this.figures = listOfFigures;
    }

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

    public List<Light> getLights() {
        return lights;
    }

    public void setLights(List<Light> lights) {
        this.lights = lights;
    }

    public int getTextureWidth() {
        return textureWidth;
    }

    public void setTextureWidth(int textureWidth) {
        this.textureWidth = textureWidth;
    }

    public int getTextureHeight() {
        return textureHeight;
    }

    public void setTextureHeight(int textureHeight) {
        this.textureHeight = textureHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }
}

