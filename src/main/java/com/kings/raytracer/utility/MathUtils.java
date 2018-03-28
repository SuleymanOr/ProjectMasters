package com.kings.raytracer.utility;

import java.awt.*;

//    Helper

public class MathUtils {

    public static final double EPSILON = 0.00000001F;
    public static final double GEOMETRY_THRESHOLD = 0.0001d;
    public static final int RECURSION_DEPTH = 8;
    public static final int OFFESET_CONSTANT = -1;
    public static final int ZERO = 0;
    public static  final int UNIT = 1;

    public static double[] addVectorReturn2(double[] vec, double addition) {
        checkSize(vec);

        vec[0] += addition;
        vec[1] += addition;
        vec[2] += addition;

        return  vec;
    }


    public static Color floatArrayToColor(double[] color) {
        int r = Math.min(255, (int) Math.round(color[0] * 255));
        int g = Math.min(255, (int) Math.round(color[1] * 255));
        int b = Math.min(255, (int) Math.round(color[2] * 255));

        return new Color(r, g, b);
    }

    // Returns the square of a
    public static double sqr(double a) {
        return a * a;
    }

    // Returns the square of a - b
    public static double sqrDiff(double a, double b) {
        return (a - b) * (a - b);
    }

    // Vector addition, adds addition to vec
      public static void addVector(double[] vec, double addition[]) {
        checkSize(vec);
        checkSize(addition);

        vec[0] += addition[0];
        vec[1] += addition[1];
        vec[2] += addition[2];
    }

    public static double[] addVectorReturn(double[] vec, double addition[]) {
        checkSize(vec);
        checkSize(addition);

        vec[0] += addition[0];
        vec[1] += addition[1];
        vec[2] += addition[2];

        return  vec;
    }

    // Multiplies addition by a scalar and then adds the result to vec
    public static double[] multiplyVector(double[] vec, double multiply) {
        checkSize(vec);

        vec[0] *= multiply;
        vec[1] *= multiply;
        vec[2] *= multiply;


        return vec;
    }

    // Multiplies addition by a scalar and then adds the result to vec
    public static void addVectorAndMultiply(double[] vec, double addition[], double scalar) {
        checkSize(vec);
        checkSize(addition);

        vec[0] += addition[0] * scalar;
        vec[1] += addition[1] * scalar;
        vec[2] += addition[2] * scalar;
    }

    // Multiplies vec by a scalar
    public static void multiplyVectorByScalar(double[] vec, double scalar) {
        checkSize(vec);

        vec[0] *= scalar;
        vec[1] *= scalar;
        vec[2] *= scalar;
    }

    public static double[] multiplyVectorByScalarReturn(double[] vec, double scalar) {
        checkSize(vec);

        vec[0] *= scalar;
        vec[1] *= scalar;
        vec[2] *= scalar;

        return vec;
    }

    public static double[] SolveQuadric(double[] c) {
        // Dim the roots array
        double[] s = new double[0];

        double p, q, D;

        /* normal form: x^2 + px + q = 0 */

        p = c[1] / (2 * c[2]);
        q = c[0] / c[2];

        D = p * p - q;

        if (IsZero(D)) {
            s = new double[1];
            s[0] = -p;
        } else if (D > 0) {
            double sqrt_D = Math.sqrt(D);
            s = new double[2];
            s[0] = sqrt_D - p;
            s[1] = -sqrt_D - p;
        }

        return s;
    }

    public static double[] SolveQuartic(double[] c) {
        // Dim the roots array
        double[] s = new double[4];

        double[] coeffs = new double[4];
        double z, u, v, sub;
        double A, B, C, D;
        double sq_A, p, q, r;
        int i;

        boolean noRoots = false;

        /* normal form: x^4 + Ax^3 + Bx^2 + Cx + D = 0 */
        A = c[3] / c[4];
        B = c[2] / c[4];
        C = c[1] / c[4];
        D = c[0] / c[4];

        //
        // substitute x = y - A/4 to eliminate cubic term:
        // x^4 + px^2 + qx + r = 0
        //
        sq_A = A * A;
        p = -3.0 / 8 * sq_A + B;
        q = 1.0 / 8 * sq_A * A - 1.0 / 2 * A * B + C;
        r = -3.0 / 256 * sq_A * sq_A + 1.0 / 16 * sq_A * B - 1.0 / 4 * A * C
                + D;

        if (IsZero(r)) {
            /* no absolute term: y(y^3 + py + q) = 0 */
            coeffs[0] = q;
            coeffs[1] = p;
            coeffs[2] = 0;
            coeffs[3] = 1;

            s = new double[3];
            double[] sTemp = SolveCubic(coeffs);

            for (int j = 0; j < sTemp.length; j++) {
                s[j] = sTemp[j];
            }
            s[2] = 0;
        } else {
            /* solve the resolvent cubic ... */
            coeffs[0] = 1.0 / 2 * r * p - 1.0 / 8 * q * q;
            coeffs[1] = -r;
            coeffs[2] = -1.0 / 2 * p;
            coeffs[3] = 1;

            double[] roots;
            roots = SolveCubic(coeffs);

            /* ... and take the one real solution ... */
            z = roots[0];

            /* ... to build two quadric equations */
            u = z * z - r;
            v = 2 * z - p;

            if (IsZero(u))
                u = 0;
            else if (u > 0)
                u = Math.sqrt(u);
            else
                noRoots = true;

            if (IsZero(v))
                v = 0;
            else if (v > 0)
                v = Math.sqrt(v);
            else
                noRoots = true;

            if (!noRoots) {
                coeffs[0] = z - u;
                coeffs[1] = q < 0 ? -v : v;
                coeffs[2] = 1;

                roots = SolveQuadric(coeffs);

                coeffs[0] = z + u;
                coeffs[1] = q < 0 ? v : -v;
                coeffs[2] = 1;

                double[] secondQuadric = SolveQuadric(coeffs);

                s = new double[roots.length + secondQuadric.length];
                //if(s == null || s.length == 0) return s;
                for (i = 0; i < roots.length; i++) {
                    s[i] = roots[i];
                }

                //if(secondQuadric == null || secondQuadric.length == 0) return s;
                for (i = roots.length; i < s.length; i++) {
                    s[i] = secondQuadric[i - roots.length];
                }
            }
        }

        /* resubstitute */
        if (!noRoots) {
            sub = 1.0 / 4 * A;

            for (i = 0; i < s.length; ++i)
                s[i] -= sub;
        }

        return s;
    }

    public static double[] SolveCubic(double[] c) {
        // Dim the roots array
        double[] s = new double[0];

        int i;
        double sub;
        double A, B, C;
        double sq_A, p, q;
        double cb_p, D;

        /* normal form: x^3 + Ax^2 + Bx + C = 0 */
        A = c[2] / c[3];
        B = c[1] / c[3];
        C = c[0] / c[3];

        /*
         * substitute x = y - A/3 to eliminate quadric term: x^3 +px + q = 0
         */
        sq_A = A * A;
        p = 1.0 / 3 * (-1.0 / 3 * sq_A + B);
        q = 1.0 / 2 * (2.0 / 27 * A * sq_A - 1.0 / 3 * A * B + C);

        /* use Cardano's formula */
        cb_p = p * p * p;
        D = q * q + cb_p;

        if (IsZero(D)) {
            if (IsZero(q))
                /* one triple solution */ {
                s = new double[1];
                s[0] = 0;
            } else
                /* one single and one double solution */ {
                double u = Math.cbrt(-q);
                s = new double[2];
                s[0] = 2 * u;
                s[1] = -u;
            }
        } else if (D < 0)
            /* Casus irreducibilis: three real solutions */ {
            double phi = 1.0 / 3 * Math.acos(-q / Math.sqrt(-cb_p));
            double t = 2 * Math.sqrt(-p);

            s = new double[3];
            s[0] = t * Math.cos(phi);
            s[1] = -t * Math.cos(phi + Math.PI / 3);
            s[2] = -t * Math.cos(phi - Math.PI / 3);
        } else
            /* one real solution */ {
            double sqrt_D = Math.sqrt(D);
            double u = Math.cbrt(sqrt_D - q);
            double v = -Math.cbrt(sqrt_D + q);

            s = new double[1];
            s[0] = u + v;
        }

        /* resubstitute */
        sub = 1.0 / 3 * A;

        for (i = 0; i < s.length; ++i)
            s[i] -= sub;

        return s;
    }


    public static final boolean IsZero(double x) {
        return ((x) > -EPSILON && (x) < EPSILON);
    }


    public static double dotProduct(double[] vec1, double[] vec2) {
        checkSize(vec1);
        checkSize(vec2);

        return vec1[0] * vec2[0]  +  vec1[1] * vec2[1]  +  vec1[2] * vec2[2] ;
    }

    public static double[] calcPointsDiff(double[] p1, double[] p2) {
        checkSize(p1);
        checkSize(p2);

        return new double [] { p2[0] - p1[0] , p2[1] - p1[1] , p2[2] - p1[2] };
    }

    // Returns the norm of the difference between this vector's position point and another point
    public static double norm(double[] p) {
        checkSize(p);

        return Math.sqrt(sqr(p[0]) + sqr(p[1]) + sqr(p[2]));
    }


    public static void normalize(double[] vec) {
        checkSize(vec);

        double norm = norm(vec);

        if (norm == 0)
            return;

        vec[0] /= norm;
        vec[1] /= norm;
        vec[2] /= norm;
    }

    public static double[] normalizeReturn(double[] vec) {
        checkSize(vec);

        double norm = norm(vec);

        if (norm == 0)
            return new double[]{0,0,0};

        vec[0] /= norm;
        vec[1] /= norm;
        vec[2] /= norm;

        return vec;
    }

    // Returns the cross product of 2 vectors
    public static double[] crossProduct(double[] d1, double[] d2) {
        checkSize(d1);
        checkSize(d2);

        return new double[]{ (d1[1] * d2[2]) - (d1[2] * d2[1]), (d1[2] * d2[0]) - (d1[0] * d2[2]), (d1[0] * d2[1]) - (d1[1] * d2[0]) };
    }

    // Reflects a vector around a normal vector. both vectors are assumed to have the same shift from the origin
    public static double[] reflectVector(double[] vec, double[] normal) {
        checkSize(vec);
        checkSize(normal);

        double dotProduct = MathUtils.dotProduct(vec, normal);

        return new double[] { -vec[0] + 2 * normal[0] * dotProduct,
                -vec[1] + 2 * normal[1] * dotProduct,
                -vec[2] + 2 * normal[2] * dotProduct };
    }

    // Returns the vector opposite to vec
    public static double[] oppositeVector(double[] vec) {
        return new double[] { -vec[0], -vec[1], -vec[2] };
    }


    public static boolean arePointsCollinear(double[] p0, double[] p1, double[] p2) {
        checkSize(p0);
        checkSize(p1);
        checkSize(p2);

        // coefficients for testing collinearity
        double a,b,c;

        // Define the vectors between pairs of the given points
        double[] vec1 = { p1[0] - p0[0] , p1[1] - p0[1] , p1[2] - p0[2] };
        double[] vec2 = { p2[0] - p0[0] , p2[1] - p0[1] , p2[2] - p0[2] };

        a = vec1[0] / vec2[0];
        b = vec1[1] / vec2[1];
        c = vec1[2] / vec2[2];

        // If all coefficients are equal then some scalar exists which scales between the vectors
        // e.g. they are linearly dependent and all 3 points are on the same line
        return a == b && b == c;

    }


    public static double[] solveQuadraticEquation(double a, double b, double c) {

        double[] roots = new double[2];
        if(a == 0)
        {
            roots[0] = -c / b;
        }
        else
        {
            double discriminant = MathUtils.sqr(b) - 4 * a * c;

            if (discriminant < 0)
            {
                roots[0] = Double.POSITIVE_INFINITY;
            }
            else if(discriminant == 0)
            {
                roots[0] =  (-b) / (2 * a);
            }
            else
            {
                discriminant = Math.sqrt(discriminant);
                double denominator = 2 * a;
                roots[0] = (-b + discriminant) / (denominator);
                roots[1] = (-b - discriminant) / (denominator);

            }
        }
        return roots;
    }

    public static double[] multiplyScalar(double[] vec, double t) {
        checkSize(vec);

        vec[0] *= t;
        vec[1] *= t;
        vec[2] *= t;
        return vec;
    }

    public static double[] addPoints(double[] a, double[] b) {
        checkSize(a);
        checkSize(b);

        return new double[]{ a[0] + b[0] , a[1] + b[1] , a[2] + b[2] };
    }

    private static void checkSize(double[] vec) {
        if(vec == null || vec.length != 3) {
            throw new IllegalArgumentException("vector should have 3 values to prevent ArrayIndexOutOfBoundsException");
        }
    }

}
