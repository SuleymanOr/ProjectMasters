package com.kings.raytracer.service;

import com.kings.raytracer.auxiliary.Camera;
import com.kings.raytracer.auxiliary.Intersection;
import com.kings.raytracer.auxiliary.Ray;
import com.kings.raytracer.utility.MathUtils;
import com.kings.raytracer.utility.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;

@Service
public class RayTracer {

    @Autowired
    RayService rayService;

    private double pixelWidth;
    private double pixelHeight;

    protected Ray buildRay(int x, int y, double offsetX, double offsetY, Scene scene, Camera camera) throws Exception {
        Ray ray = new Ray(camera.getEye(), camera.getDirection(), camera.getScreenDist());
        double[] endPoint = ray.getEndPoint();

        double imageHeightOffsset = (y - (scene.getImageHeight() / 2));
        double upOffsetValue = MathUtils.OFFESET_CONSTANT * ((imageHeightOffsset) - (offsetY /scene.getSuperSampleValue())) * pixelHeight;

        double imageWidthOffsset = (x - (scene.getImageWidth() / 2));
        double rightOffsetValue = ((imageWidthOffsset) + (offsetX / scene.getSuperSampleValue())) * pixelWidth;

        MathUtils.addVectorAndMultiply(endPoint, camera.getViewPlaneUp(), upOffsetValue);
        MathUtils.addVectorAndMultiply(endPoint, camera.getRightDirection(), rightOffsetValue);

        ray.setDirection(MathUtils.calcPointsDiff(camera.getEye(), endPoint));
        ray.normalize();
        return ray;
    }


    protected int getRayHits(Scene scene, Camera camera, int y, int x, int hits, double[] color) throws Exception {
        Intersection intersection;
        Ray ray;
        for (int k = 0; k < scene.getSuperSampleValue(); k++) {
            for (int l = 0; l < scene.getSuperSampleValue(); l++) {
                double[] sampleColor;

                ray = buildRay(x, y, k, l, scene, camera);
                intersection = rayService.findIntersection(ray, scene);

                if (intersection.getFigure() != null) {
                    hits++;
                    sampleColor = rayService.getObjectColor(ray, intersection, 1, scene);
                    MathUtils.addVector(color, sampleColor);

                    ray.setMagnitude(intersection.getDistance());
                }
            }
        }
        return hits;
    }

    public BufferedImage renderImage(Scene scene, Camera camera) throws Exception {
        BufferedImage bufferedImage = new BufferedImage(scene.getImageWidth(), scene.getImageHeight(), BufferedImage.TYPE_INT_RGB);
        int hitNumber ;
        double[] color;

        pixelWidth = camera.getScreenWidth() / scene.getImageWidth();
        pixelHeight = scene.getImageWidth() / scene.getImageHeight() * pixelWidth;

        for (int y = 0; y < scene.getImageHeight(); y++) {
            for (int x = 0; x < scene.getImageWidth(); x++) {
                hitNumber = 0;
                color = new double[3];

                hitNumber = getRayHits(scene, camera, y, x, hitNumber, color);
                if (hitNumber == 0) {
                    color = scene.getBackgroundAt(x, y);
                } else {
                    MathUtils.multiplyVectorByScalar(color, 1F / hitNumber);
                }

                Color finalColor = MathUtils.floatArrayToColor(color);
                bufferedImage.setRGB(x,y,finalColor.getRGB());
            }
        }
        return bufferedImage;
    }

}

