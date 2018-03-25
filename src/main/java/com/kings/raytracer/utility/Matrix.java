package com.kings.raytracer.utility;

public class Matrix {

    private double[][] A;
    private int mRow;
    private int mCol;

    public Matrix (int numRow, int numCol) {
        mRow = numRow;
        mCol = numCol;
        A = new double[numRow][numCol];
    }

    public void setValue (int i, int j, double value) {
        A[i][j] = value;
    }

    public double getValue (int i, int j) {
        return A[i][j];
    }

    public static Matrix generateIdentity (int mRow, int mCol) {
        Matrix A = new Matrix(mRow,mCol);
        double[][] X = A.getA();
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mCol; j++) {
                X[i][j] = (i == j ? 1.0 : 0.0);
            }
        }
        return A;
    }

    public Matrix times (Matrix B) {
        if (B.mRow != mCol) {
            throw new IllegalArgumentException("Matrix inner dimensions must agree.");
        }
        Matrix X = new Matrix(mRow,B.mCol);
        double[][] C = X.getA();
        double[] Bcolj = new double[mCol];
        for (int j = 0; j < B.mCol; j++) {
            for (int k = 0; k < mCol; k++) {
                Bcolj[k] = B.A[k][j];
            }
            for (int i = 0; i < mRow; i++) {
                double[] Arowi = A[i];
                double s = 0;
                for (int k = 0; k < mCol; k++) {
                    s += Arowi[k]*Bcolj[k];
                }
                C[i][j] = s;
            }
        }
        return X;
    }

    public double[][] getA() {
        return A;
    }

    public void setA(double[][] a) {
        A = a;
    }
}
