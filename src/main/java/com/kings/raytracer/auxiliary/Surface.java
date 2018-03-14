package com.kings.raytracer.auxiliary;



public class Surface {

    private static final int TYPE_FLAT = 1;
    public static final int TYPE_CHECKERS = 2;
    public static final int TYPE_TEXTURE = 3;

    private int typeId;
    private String type;
//    private double[] diffuse = {0.8F, 0.8F, 0.8F};
    private double[] diffuse = {0.98F, 0.48F, 0.4F};
    private double[] specular = {1.0F, 1.0F, 1.0F};
    private double[] ambient = {0.1F, 0.1F, 0.1F};
    private double[] emission = {0, 0, 0};
    private double shininess = 100.0F;
    private double checkersSize = 0.1F;
    private double[] checkersDiffuse1 = {1.0F, 1.0F, 1.0F};
    private double[] checkersDiffuse2 = {0.1F, 0.1F, 0.1F};
    private double reflectance = 0.0F;
    private String textureFileName;
    private int textureWidth;
    private int textureHeight;
    private double[][][] texture;

    // Returns the texture color for a given 2D point in [0, 1] coordinates
    public double[] getTextureColor(double[] point2D) {
        int textureX = Math.abs((int) Math.round(point2D[0] * textureWidth)) % textureWidth;
        int textureY = Math.abs((int) Math.round(point2D[1] * textureHeight)) % textureHeight;

        return texture[textureY][textureX];
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double[] getDiffuse() {
        return diffuse;
    }

    public void setDiffuse(double[] diffuse) {
        this.diffuse = diffuse;
    }

    public double[] getSpecular() {
        return specular;
    }

    public void setSpecular(double[] specular) {
        this.specular = specular;
    }

    public double[] getAmbient() {
        return ambient;
    }

    public void setAmbient(double[] ambient) {
        this.ambient = ambient;
    }

    public double[] getEmission() {
        return emission;
    }

    public void setEmission(double[] emission) {
        this.emission = emission;
    }

    public double getShininess() {
        return shininess;
    }

    public void setShininess(double shininess) {
        this.shininess = shininess;
    }

    public double getCheckersSize() {
        return checkersSize;
    }

    public void setCheckersSize(double checkersSize) {
        this.checkersSize = checkersSize;
    }

    public double[] getCheckersDiffuse1() {
        return checkersDiffuse1;
    }

    public void setCheckersDiffuse1(double[] checkersDiffuse1) {
        this.checkersDiffuse1 = checkersDiffuse1;
    }

    public double[] getCheckersDiffuse2() {
        return checkersDiffuse2;
    }

    public void setCheckersDiffuse2(double[] checkersDiffuse2) {
        this.checkersDiffuse2 = checkersDiffuse2;
    }

    public double[][][] getTexture() {
        return texture;
    }

    public void setTexture(double[][][] texture) {
        this.texture = texture;
    }

    public double getReflectance() {
        return reflectance;
    }

    public void setReflectance(double reflectance) {
        this.reflectance = reflectance;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
