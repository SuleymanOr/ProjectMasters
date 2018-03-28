package com.kings.raytracer.auxiliary;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kings.raytracer.utility.MathUtils;

/*Helper class responsible for setting the camera values to inspect different
* parts of the scene*/
public class Camera {

    private double[] eye;
    private double[] lookAt;
    private double[] direction;
    private double[] upDirection;
    private double[] rightDirection;
    private double[] viewPlaneUp;
    private double screenDist = 1;
    private double screenWidth = 2;

    public Camera(){}

    @JsonCreator
    public Camera(@JsonProperty("eye")double[] eye,
                  @JsonProperty("lookAt")double[] lookAt,
                  @JsonProperty("upDirection")double[] upDirection,
                  @JsonProperty("screenDist")double screenDist,
                  @JsonProperty("screenWidth")double screenWidth) {
        this.eye = eye;
        this.lookAt = lookAt;
        this.upDirection = upDirection;
        this.direction = MathUtils.normalizeReturn(MathUtils.calcPointsDiff(eye, lookAt));
        this.rightDirection = MathUtils.multiplyVectorByScalarReturn(MathUtils.normalizeReturn(MathUtils.crossProduct(upDirection, direction)), -1);
        this.viewPlaneUp = MathUtils.normalizeReturn(MathUtils.crossProduct(this.rightDirection, this.direction));
        this.screenDist = screenDist;
        this.screenWidth = screenWidth;
    }

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

    public double getScreenDist() {
        return screenDist;
    }

    public void setScreenDist(double screenDist) {
        this.screenDist = screenDist;
    }

    public double getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(double screenWidth) {
        this.screenWidth = screenWidth;
    }

    public double[] getUpDirection() {
        return upDirection;
    }

    public void setUpDirection(double[] upDirection) {
        this.upDirection = upDirection;
    }

    public double[] getRightDirection() {
        return rightDirection;
    }

    public void setRightDirection(double[] rightDirection) {
        this.rightDirection = rightDirection;
    }

    public double[] getDirection() {
        return direction;
    }

    public void setDirection(double[] direction) {
        this.direction = direction;
    }

    public double[] getViewPlaneUp() {
        return viewPlaneUp;
    }

    public void setViewPlaneUp(double[] viewPlaneUp) {
        this.viewPlaneUp = viewPlaneUp;
    }

}

