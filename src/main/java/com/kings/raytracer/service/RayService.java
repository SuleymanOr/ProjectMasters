package com.kings.raytracer.service;

import com.kings.raytracer.auxiliary.Intersection;
import com.kings.raytracer.auxiliary.Ray;
import com.kings.raytracer.geometry.Figure;
import com.kings.raytracer.light.Light;
import com.kings.raytracer.utility.MathUtils;
import com.kings.raytracer.utility.Scene;
import org.springframework.stereotype.Service;

@Service
public class RayService {


    public Intersection findIntersection(final Ray ray, Scene scene) {
        double minDistance = Double.POSITIVE_INFINITY;
        double intersection;
        Figure minFigure = null;

        for (Figure figure : scene.getFigures()) {

            intersection = figure.intersect(ray);
            if (intersection < minDistance && intersection > MathUtils.EPSILON) {
                minFigure = figure;
                minDistance = intersection;
            }
        }

        return new Intersection(minDistance, minFigure);
    }


    public double[] getObjectColor(Ray ray, Intersection intersection, int recursionDepth, Scene scene) throws Exception {

        if (recursionDepth > MathUtils.RECURSION_DEPTH)
            return new double[]{0, 0, 0};

        Figure figure = intersection.getFigure();

        if (figure == null)
            return scene.getBackgroundColor();

        double[] color = new double[3];
        double[] specularLight = figure.getSpecular();

        ray.setMagnitude(intersection.getDistance());

        double[] pointOfIntersection = ray.getEndPoint();
        double[] diffuseLight = figure.getColorAt(pointOfIntersection);

        ray.setMagnitude(intersection.getDistance() - 1);

        double[] normalLight = figure.getNormal(pointOfIntersection);
        shootLight(ray, scene, figure, color, specularLight, pointOfIntersection, diffuseLight, normalLight);
        findReflection(ray, recursionDepth, scene, figure, color, pointOfIntersection, normalLight);

        return color;
    }

    private void findReflection(Ray ray, int recursionDepth, Scene scene, Figure figure, double[] color, double[] pointOfIntersection, double[] normalLight) throws Exception {
        double[] sceneLightAmbient = scene.getAmbientLight();
        double[] surfaceLightAmbient = figure.getAmbient();

        color[0] += sceneLightAmbient[0] * surfaceLightAmbient[0];
        color[1] += sceneLightAmbient[1] * surfaceLightAmbient[1];
        color[2] += sceneLightAmbient[2] * surfaceLightAmbient[2];

        double[] figureEmission = figure.getEmission();
        color[0] += figureEmission[0];
        color[1] += figureEmission[1];
        color[2] += figureEmission[2];

        double[] reflectionDirection = MathUtils.reflectVector(MathUtils.oppositeVector(ray.getDirection()), normalLight);
        Ray reflectionRay = new Ray(pointOfIntersection, reflectionDirection, 1);
        reflectionRay.normalize();

        Intersection reflectionIntersection = findIntersection(reflectionRay, scene);
        double[] reflectionColor = getObjectColor(reflectionRay, reflectionIntersection, recursionDepth + 1, scene);
        MathUtils.addVectorAndMultiply(color, reflectionColor, figure.getReflectance());
    }

    private void shootLight(Ray ray, Scene scene, Figure figure, double[] color, double[] specularLight, double[] pointOfIntersection, double[] diffuseLight, double[] normalLight) throws Exception {
        for (Light light : scene.getLights()) {
            double[] vectorToLight = light.getVectorToLight(pointOfIntersection);

            Ray rayToLight = new Ray(pointOfIntersection, vectorToLight, 1);
            rayToLight.normalize();

            double distanceToFigure = findIntersection(rayToLight, scene).getDistance();
            double distanceToLight = MathUtils.norm(MathUtils.calcPointsDiff(pointOfIntersection, light.getPosition()));
            boolean lightVisible = distanceToFigure <= MathUtils.EPSILON || distanceToFigure >= distanceToLight;

            if (lightVisible) {
                checkAllLightAtIntersection(ray, figure, color, specularLight, pointOfIntersection, diffuseLight, normalLight, light, vectorToLight);
            }
        }
    }

    private void checkAllLightAtIntersection(Ray ray, Figure figure, double[] color, double[] specularLight, double[] pointOfIntersection, double[] diffuseLight, double[] normalLight, Light light, double[] vectorToLight) {
        double[] amountOfLightAtIntersection = light.getAmountOfLight(pointOfIntersection);

        double visibleDiffuseLight = MathUtils.dotProduct(vectorToLight, normalLight);
        if (visibleDiffuseLight > 0) {

            color[0] += diffuseLight[0] * amountOfLightAtIntersection[0] * visibleDiffuseLight;
            color[1] += diffuseLight[1] * amountOfLightAtIntersection[1] * visibleDiffuseLight;
            color[2] += diffuseLight[2] * amountOfLightAtIntersection[2] * visibleDiffuseLight;
        }

        double[] reflectedVectorToLight = MathUtils.reflectVector(vectorToLight, normalLight);
        MathUtils.normalize(reflectedVectorToLight);

        double visibleSpecularLight = MathUtils.dotProduct(reflectedVectorToLight, ray.getDirection());

        if (visibleSpecularLight < 0) {
            visibleSpecularLight = Math.pow(Math.abs(visibleSpecularLight), figure.getShininess());

            color[0] += specularLight[0] * amountOfLightAtIntersection[0] * visibleSpecularLight;
            color[1] += specularLight[1] * amountOfLightAtIntersection[1] * visibleSpecularLight;
            color[2] += specularLight[2] * amountOfLightAtIntersection[2] * visibleSpecularLight;
        }
    }

}
