package com.cgvsu.math;

public class Matrix4f extends Matrix {

    public Matrix4f(double[][] matrix) {
        super(matrix);
    }

    public Matrix4f(double[] matrix, int num) {
        super(matrix, num);
    }

    public Matrix4f() {
        super(4);
    }

    public Matrix4f(Matrix matrix) {
        super(matrix);
    }

    public static Matrix4f addition(Matrix4f m1, Matrix4f m2) {
        double[][] m = new double[4][4];
        for (int i = 0; i < m1.getMatrixArray().length; i++) {
            for (int j = 0; j < m1.getMatrixArray()[0].length; j++) {
                m[i][j] = m1.getMatrixArray()[i][j] + m2.getMatrixArray()[i][j];
            }
        }
        return new Matrix4f(m);
    }

    public static Matrix4f subtraction(Matrix4f m1, Matrix4f m2) {
        double[][] m = new double[4][4];
        for (int i = 0; i < m1.getMatrixArray().length; i++) {
            for (int j = 0; j < m1.getMatrixArray()[0].length; j++) {
                m[i][j] = m1.getMatrixArray()[i][j] - m2.getMatrixArray()[i][j];
            }
        }
        return new Matrix4f(m);
    }

    public static Vector4f multiplierVector(Matrix4f m1, Vector4f v1) {
        Vector4f v = new Vector4f(0, 0, 0, 0);
        for (int i = 0; i < m1.getMatrixArray().length; i++) {
            for (int j = 0; j < m1.getMatrixArray()[0].length; j++) {
                v.getCoordinates()[i] += m1.getMatrixArray()[i][j] * v1.getCoordinates()[j];
            }
        }
        return v;
    }

    public static Matrix4f matrixMultiplier(Matrix4f m1, Matrix4f m2) {
        double[][] m = new double[4][4];
        for (int i = 0; i < m1.getMatrixArray().length; i++) {
            for (int j = 0; j < m1.getMatrixArray()[0].length; j++) {
                for (int k = 0; k < m1.getMatrixArray().length; k++) {
                    m[i][j] += m1.getMatrixArray()[i][k] * m2.getMatrixArray()[k][j];
                }
            }
        }
        return new Matrix4f(m);
    }

    public static Matrix4f getOneMatrix() {
        return new Matrix4f(Matrix.getOneMatrix(4).getMatrixArray());
    }

    public Matrix4f transpose() {
        return new Matrix4f(Matrix.transpose(super.getMatrixArray(), 4).getMatrixArray());
    }

    public Matrix4f inversion() {
        return new Matrix4f(Matrix.inversion(super.getMatrixArray()).getMatrixArray());
    }}
