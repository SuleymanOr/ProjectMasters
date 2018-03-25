package com.kings.raytracer.geometry;

import com.kings.raytracer.auxiliary.Ray;
import com.kings.raytracer.utility.MathUtils;

public class Cube extends Figure{
    private double[] p0, p1, p2, p3;
    private Rectangle[] rectangles = new Rectangle[6];
    private Rectangle currentIntersectingRectangle = null;
    private int intersectingRectangleIndex = 0;


    public Cube(double[] p0, double[] p1, double[] p2, double[] p3,double[] color) {
        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        //this.setAmbient(new double[]{1,1,1});
        this.setSpecular(new double[]{0.7,0.7,0.7});
        this.setDiffuse(color);
        this.setShininess(20);
        this.setSurfaceType("Checkers");
        setAdditionalValues();
    }

    /**

     * Get the normal of the rectangle which is currently intersected.

     */

    @Override

    public double[] getNormal(double[] point) throws Exception {

        return currentIntersectingRectangle.getNormal(point);

    }


    @Override

    public double intersect(Ray ray) {

        /**double[] p0_p1 = MathUtils.calcPointsDiff(p0, p1);
        double[] p0_p3 = MathUtils.calcPointsDiff(p0, p3);

        // Assume this is a cube just for the documentation

        rectangles[0] = new Rectangle(p0, p1, p2);        // Front facing rectangle
        rectangles[1] = new Rectangle(p0, p2, p3);        // Left facing rectangle
        rectangles[2] = new Rectangle(p0, p3, p1);        // Bottom facing rectangle
        rectangles[3] = new Rectangle(p1, MathUtils.addPoints(p3, p0_p1), MathUtils.addPoints(p2, p0_p1));        // Right facing rectangle
        rectangles[4] = new Rectangle(p2, MathUtils.addPoints(p2, p0_p1), MathUtils.addPoints(p2, p0_p3));        // Top facing rectangle
        rectangles[5] = new Rectangle(p3, MathUtils.addPoints(p2, p0_p3), MathUtils.addPoints(p3, p0_p1));        // Back facing rectangle
        */

        // Start off with infinite distance and no intersecting primitive

        double minDistance = Double.POSITIVE_INFINITY;

        for (int i = 0; i < rectangles.length; i++) {

            double t = rectangles[i].intersect(ray);

            // If we found a closer intersecting rectangle, keep a reference to and it

            if (t < minDistance) {

                minDistance = t;

                currentIntersectingRectangle = rectangles[i];

                intersectingRectangleIndex = i;

            }

        }



        return minDistance;

    }

    public void setAdditionalValues(){
        double[] p0_p1 = MathUtils.calcPointsDiff(p0, p1);
        double[] p0_p3 = MathUtils.calcPointsDiff(p0, p3);

        // Assume this is a cube just for the documentation

        rectangles[0] = new Rectangle(p0, p1, p2, new double[]{0.8, 0.8, 0.8});        // Front facing rectangle
        rectangles[1] = new Rectangle(p0, p2, p3, new double[]{0.8, 0.8, 0.8});        // Left facing rectangle
        rectangles[2] = new Rectangle(p0, p3, p1, new double[]{0.8, 0.8, 0.8});        // Bottom facing rectangle
        rectangles[3] = new Rectangle(p1, MathUtils.addPoints(p3, p0_p1), MathUtils.addPoints(p2, p0_p1), new double[]{0.8, 0.8, 0.8});        // Right facing rectangle
        rectangles[4] = new Rectangle(p2, MathUtils.addPoints(p2, p0_p1), MathUtils.addPoints(p2, p0_p3), new double[]{0.8, 0.8, 0.8});        // Top facing rectangle
        rectangles[5] = new Rectangle(p3, MathUtils.addPoints(p2, p0_p3), MathUtils.addPoints(p3, p0_p1), new double[]{0.8, 0.8, 0.8});        // Back facing rectangle

    }

    @Override
    public double[] getTexturePoints(double[] point) {
        return rectangles[intersectingRectangleIndex].getTexturePoints(point);
    }
}
