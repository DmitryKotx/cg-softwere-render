package com.cgvsu.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyMatrixTest {

    @Test
    void getMatrixArray() {
        double[] first = {1, 2, 3};
        double[] second = {4, 5, 6};
        double[] third = {7, 8, 9};
        double[][] matrix = new double[3][3];
        matrix[0] = first;
        matrix[1] = second;
        matrix[2] = third;
        Matrix3f answer = new Matrix3f(matrix);
        assertEquals(answer.getMatrixArray()[0][0], matrix[0][0]);
        assertEquals(answer.getMatrixArray()[0][1], matrix[0][1]);
        assertEquals(answer.getMatrixArray()[0][2], matrix[0][2]);
        assertEquals(answer.getMatrixArray()[1][0], matrix[1][0]);
        assertEquals(answer.getMatrixArray()[1][1], matrix[1][1]);
        assertEquals(answer.getMatrixArray()[1][2], matrix[1][2]);
        assertEquals(answer.getMatrixArray()[2][0], matrix[2][0]);
        assertEquals(answer.getMatrixArray()[2][1], matrix[2][1]);
        assertEquals(answer.getMatrixArray()[2][2], matrix[2][2]);
    }

    @Test
    void transposeMatrix() {
        double[] first = {1, 2, 3};
        double[] second = {4, 5, 6};
        double[] third = {7, 8, 9};
        double[][] matrix = new double[3][3];
        double[] firstT = {1, 4, 7};
        double[] secondT = {2, 5, 8};
        double[] thirdT = {3, 6, 9};
        double[][] matrixT = new double[3][3];
        matrix[0] = first;
        matrix[1] = second;
        matrix[2] = third;
        matrixT[0] = firstT;
        matrixT[1] = secondT;
        matrixT[2] = thirdT;
        Matrix3f answer = new Matrix3f(matrix);
        assertEquals(answer.transpose().getMatrixArray()[0][0], matrixT[0][0]);
        assertEquals(answer.transpose().getMatrixArray()[0][1], matrixT[0][1]);
        assertEquals(answer.transpose().getMatrixArray()[0][2], matrixT[0][2]);
        assertEquals(answer.transpose().getMatrixArray()[1][0], matrixT[1][0]);
        assertEquals(answer.transpose().getMatrixArray()[1][1], matrixT[1][1]);
        assertEquals(answer.transpose().getMatrixArray()[1][2], matrixT[1][2]);
        assertEquals(answer.transpose().getMatrixArray()[2][0], matrixT[2][0]);
        assertEquals(answer.transpose().getMatrixArray()[2][1], matrixT[2][1]);
        assertEquals(answer.transpose().getMatrixArray()[2][2], matrixT[2][2]);
    }

    @Test
    void getZeroMatrix() {
        double[] first = {0, 0, 0, 0};
        double[] second = {0, 0, 0, 0};
        double[] third = {0, 0, 0, 0};
        double[] fourth = {0, 0, 0, 0};
        double[][] matrix = new double[4][4];
        matrix[0] = first;
        matrix[1] = second;
        matrix[2] = third;
        matrix[3] = fourth;
        Matrix answer = Matrix.getZeroMatrix(4);
        assertEquals(answer.getMatrixArray()[0][0], matrix[0][0]);
        assertEquals(answer.getMatrixArray()[0][1], matrix[0][1]);
        assertEquals(answer.getMatrixArray()[0][2], matrix[0][2]);
        assertEquals(answer.getMatrixArray()[0][3], matrix[0][3]);
        assertEquals(answer.getMatrixArray()[1][0], matrix[1][0]);
        assertEquals(answer.getMatrixArray()[1][1], matrix[1][1]);
        assertEquals(answer.getMatrixArray()[1][2], matrix[1][2]);
        assertEquals(answer.getMatrixArray()[1][3], matrix[1][3]);
        assertEquals(answer.getMatrixArray()[2][0], matrix[2][0]);
        assertEquals(answer.getMatrixArray()[2][1], matrix[2][1]);
        assertEquals(answer.getMatrixArray()[2][2], matrix[2][2]);
        assertEquals(answer.getMatrixArray()[2][3], matrix[2][3]);
        assertEquals(answer.getMatrixArray()[3][0], matrix[3][0]);
        assertEquals(answer.getMatrixArray()[3][1], matrix[3][1]);
        assertEquals(answer.getMatrixArray()[3][2], matrix[3][2]);
        assertEquals(answer.getMatrixArray()[3][3], matrix[3][3]);
    }

    @Test
    void getOneMatrix() {
        double[] first = {1, 0, 0, 0};
        double[] second = {0, 1, 0, 0};
        double[] third = {0, 0, 1, 0};
        double[] fourth = {0, 0, 0, 1};
        double[][] matrix = new double[4][4];
        matrix[0] = first;
        matrix[1] = second;
        matrix[2] = third;
        matrix[3] = fourth;
        Matrix answer = Matrix.getOneMatrix(4);
        assertEquals(answer.getMatrixArray()[0][0], matrix[0][0]);
        assertEquals(answer.getMatrixArray()[0][1], matrix[0][1]);
        assertEquals(answer.getMatrixArray()[0][2], matrix[0][2]);
        assertEquals(answer.getMatrixArray()[0][3], matrix[0][3]);
        assertEquals(answer.getMatrixArray()[1][0], matrix[1][0]);
        assertEquals(answer.getMatrixArray()[1][1], matrix[1][1]);
        assertEquals(answer.getMatrixArray()[1][2], matrix[1][2]);
        assertEquals(answer.getMatrixArray()[1][3], matrix[1][3]);
        assertEquals(answer.getMatrixArray()[2][0], matrix[2][0]);
        assertEquals(answer.getMatrixArray()[2][1], matrix[2][1]);
        assertEquals(answer.getMatrixArray()[2][2], matrix[2][2]);
        assertEquals(answer.getMatrixArray()[2][3], matrix[2][3]);
        assertEquals(answer.getMatrixArray()[3][0], matrix[3][0]);
        assertEquals(answer.getMatrixArray()[3][1], matrix[3][1]);
        assertEquals(answer.getMatrixArray()[3][2], matrix[3][2]);
        assertEquals(answer.getMatrixArray()[3][3], matrix[3][3]);
    }
}