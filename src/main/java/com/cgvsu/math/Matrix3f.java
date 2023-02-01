package com.cgvsu.math;

public class Matrix3f extends Matrix {

    public Matrix3f(double[][] matrix) {
        super(matrix);
    }

    public Matrix3f() {
        super(3);
    }

    public static Matrix3f addition(Matrix3f m1, Matrix3f m2) {
        double[][] m = new double[3][3];
        for(int i = 0; i < m1.getMatrixArray().length; i++){
            for(int j = 0; j < m1.getMatrixArray()[0].length; j++){
                m[i][j] =  m1.getMatrixArray()[i][j] + m2.getMatrixArray()[i][j];
            }
        }
        return new Matrix3f(m);
    }

    public static Matrix3f subtraction(Matrix3f m1, Matrix3f m2) {
        double[][] m = new double[3][3];
        for(int i = 0; i < m1.getMatrixArray().length; i++){
            for(int j = 0; j < m1.getMatrixArray()[0].length; j++){
                m[i][j] =  m1.getMatrixArray()[i][j] - m2.getMatrixArray()[i][j];
            }
        }
        return new Matrix3f(m);
    }

    public static Vector3f multiplierVector(Matrix3f m1, Vector3f v1) {
        Vector3f v = new Vector3f(0, 0, 0);
        for (int i = 0; i < m1.getMatrixArray().length; i++) {
            for (int j = 0; j < m1.getMatrixArray()[0].length; j++) {
                v.getCoordinates()[i] += m1.getMatrixArray()[i][j] * v1.getCoordinates()[j];
            }
        }
        return v;
    }

    public static Matrix3f matrixMultiplier(Matrix3f m1, Matrix3f m2) {
        double[][] m = new double[3][3];
        for (int i = 0; i < m1.getMatrixArray().length; i++) {
            for (int j = 0; j < m1.getMatrixArray()[0].length; j++) {
                for (int k = 0; k < m1.getMatrixArray().length; k++) {
                    m[i][j] += m1.getMatrixArray()[i][k] * m2.getMatrixArray()[k][j];
                }
            }
        }
        return new Matrix3f(m);
    }

    public static Matrix3f getOneMatrix() {
        return new Matrix3f(Matrix.getOneMatrix(3).getMatrixArray());
    }

    public Matrix3f transpose() {
        return new Matrix3f(Matrix.transpose(super.getMatrixArray(), 3).getMatrixArray());
    }

    public Matrix3f inversion() {
        return new Matrix3f(Matrix.inversion(super.getMatrixArray()).getMatrixArray());
    }}
