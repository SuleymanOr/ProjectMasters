package com.kings.raytracer.geometry;

import com.kings.raytracer.auxiliary.Ray;
import com.kings.raytracer.utility.MathUtils;

public class Triangle extends Figure {
    private  double[] v0, v1, v2;

    public Triangle(double[] v0, double[] v1, double[] v2,double[] color,double reflectance, String surfaceType) {
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
        this.setDiffuse(color);
        this.setReflectance(reflectance);
        this.setSurfaceType(surfaceType);
    }

    @Override
    public double intersect(Ray ray) {
        double[] T = MathUtils.calcPointsDiff( v0,ray.getPosition());

        double[] q1 = MathUtils.calcPointsDiff( v0,v1);
        double[] Q = MathUtils.crossProduct(T,q1);

        double[] q2 = MathUtils.calcPointsDiff(v0,v2);
        double[] P = MathUtils.crossProduct(ray.getDirection(), q2);

        double pDotE1 = MathUtils.dotProduct(P,q1);

        if (pDotE1 >= MathUtils.EPSILON || pDotE1 <= -MathUtils.EPSILON) {
            double u =  MathUtils.dotProduct(P,T)/ pDotE1;
            double v = MathUtils.dotProduct(Q,ray.getDirection()) / pDotE1;

            if (u >= 0 && v >= 0 && u + v <= 1) {
                double distance = MathUtils.dotProduct(Q,q2) / pDotE1;
                return distance;
            }
        }

        return Double.NaN;
    }

    public double[] getV0() {
        return v0;
    }

    public void setV0(double[] v0) {
        this.v0 = v0;
    }

    public double[] getV1() {
        return v1;
    }

    public void setV1(double[] v1) {
        this.v1 = v1;
    }

    public double[] getV2() {
        return v2;
    }

    public void setV2(double[] v2) {
        this.v2 = v2;
    }

    @Override
    public double[] getNormal(double[] point) throws Exception {
        double[] q1 = MathUtils.calcPointsDiff(v1, v0);
        double[] q2 = MathUtils.calcPointsDiff(v2, v0);
        double[] v  = MathUtils.crossProduct(q1,q2);
        MathUtils.normalize(v);
        return  v;
       /* double denominator =1d/(MathUtils.sqr(v[0]) +
                MathUtils.sqr(v[1]) +
                MathUtils.sqr(v[2]));


        double[] d = MathUtils.calcPointsDiff(point,v0);
        double[] c = MathUtils.crossProduct(d,q2);
        double l = Math.sqrt(
                MathUtils.sqr(c[0]) +
                        MathUtils.sqr(c[1]) +
                        MathUtils.sqr(c[2])
        );
        double f1 = l *denominator;

        double v = e1.crossProduct(dot.subtract(v0)).getLength() * denominator;
        double w = 1d - u - v;*/
    }

    @Override
    public double[] getTexturePoints(double[] point) {
        return new double[0];
    }
}