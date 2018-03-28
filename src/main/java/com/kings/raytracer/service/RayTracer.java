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
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/*Main service class used to render the image
* using the raytracing algortihm*/
@Service
public class RayTracer {

    @Autowired
    RayService rayService;

    private double pixelWidth;
    private double pixelHeight;

    /*General method for holding the raytracer algortihm*/
    public BufferedImage renderImage(Scene scene, Camera camera) throws Exception {
        BufferedImage bufferedImage = new BufferedImage(scene.getImageWidth(), scene.getImageHeight(), BufferedImage.TYPE_INT_RGB);
        int hitNumber ;
        double[] color;

        pixelWidth = camera.getScreenWidth() / scene.getImageWidth();
        pixelHeight = scene.getImageWidth() / scene.getImageHeight() * pixelWidth;
        final ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
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
                int finalX = x;
                int finalY = y;
                executor.execute(new Runnable() {
                    public void run() {
                        bufferedImage.setRGB(finalX, finalY, finalColor.getRGB());
                    }
                });
            }
        }
        return bufferedImage;
    }
    /*Get the hits under supersampling for better pixel accuracy*/
    protected int getRayHits(Scene scene, Camera camera, int y, int x, int hits, double[] color) throws Exception {
        for (int k = 0; k < scene.getSuperSampleValue(); k++) {
            for (int l = 0; l < scene.getSuperSampleValue(); l++) {
                hits = getHits(scene, camera, y, x, hits, color, k, l);
            }
        }
        return hits;
    }

    /*Build the ray used to shoot the scene in order to find an intersection*/
    protected Ray buildRay(int x, int y, double offsetX, double offsetY, Scene scene, Camera camera) throws Exception {
        Ray ray = new Ray(camera.getEye(), camera.getDirection(), camera.getScreenDist());
        double[] endPoint = ray.getEndPoint();

        double upOffsetValue = getUpOffsetValue(y, offsetY, scene);
        double rightOffsetValue = getRightOffsetValue(x, offsetX, scene);

        MathUtils.addVectorAndMultiply(endPoint, camera.getViewPlaneUp(), upOffsetValue);
        MathUtils.addVectorAndMultiply(endPoint, camera.getRightDirection(), rightOffsetValue);

        ray.setDirection(MathUtils.calcPointsDiff(camera.getEye(), endPoint));
        ray.normalize();
        return ray;
    }
    /*Return the number of hits done by the propagating ray*/
    private int getHits(Scene scene, Camera camera, int y, int x, int hits, double[] color, int k, int l) throws Exception {
        Ray ray;
        Intersection intersection;
        double[] sampleColor;

        ray = buildRay(x, y, k, l, scene, camera);
        intersection = rayService.findIntersection(ray, scene);

        if (intersection.getFigure() != null) {
            hits++;
            sampleColor = rayService.getObjectColor(ray, intersection, MathUtils.UNIT, scene);
            MathUtils.addVector(color, sampleColor);

            ray.setMagnitude(intersection.getDistance());
        }
        return hits;
    }

    /*Calculate offset on the x axis*/
    private double getRightOffsetValue(int x, double offsetX, Scene scene) {
        double imageWidthOffset = (x - (scene.getImageWidth() / 2));
        return ((imageWidthOffset) + (offsetX / scene.getSuperSampleValue())) * pixelWidth;
    }

    /*calculate offset on the y address*/
    private double getUpOffsetValue(int y, double offsetY, Scene scene) {
        double imageHeightOffset = (y - (scene.getImageHeight() / 2));
        return MathUtils.OFFESET_CONSTANT * ((imageHeightOffset) - (offsetY /scene.getSuperSampleValue())) * pixelHeight;
    }

}

