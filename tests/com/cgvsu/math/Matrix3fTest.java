package com.cgvsu.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Matrix3fTest {

    @Test
    void addition() {
        double[] first = {1, 2, 3};
        double[] second = {4, 5, 6};
        double[] third = {7, 8, 9};
        double[][] matrix = new double[3][3];
        matrix[0] = first;
        matrix[1] = second;
        matrix[2] = third;
        Matrix3f answer = new Matrix3f(matrix);

        double[] first2 = {1, 2, 3};
        double[] second2 = {4, 5, 6};
        double[] third2 = {7, 8, 9};
        double[][] matrix2 = new double[3][3];
        matrix2[0] = first2;
        matrix2[1] = second2;
        matrix2[2] = third2;
        Matrix3f answer2 = new Matrix3f(matrix2);

        double[] first3 = {2, 4, 6};
        double[] second3 = {8, 10, 12};
        double[] third3 = {14, 16, 18};
        double[][] matrix3 = new double[3][3];
        matrix3[0] = first3;
        matrix3[1] = second3;
        matrix3[2] = third3;

        Matrix3f answer3 = Matrix3f.addition(answer, answer2);
        assertEquals(answer3.getMatrixArray()[0][0], matrix3[0][0]);
        assertEquals(answer3.getMatrixArray()[0][1], matrix3[0][1]);
        assertEquals(answer3.getMatrixArray()[0][2], matrix3[0][2]);
        assertEquals(answer3.getMatrixArray()[1][0], matrix3[1][0]);
        assertEquals(answer3.getMatrixArray()[1][1], matrix3[1][1]);
        assertEquals(answer3.getMatrixArray()[1][2], matrix3[1][2]);
        assertEquals(answer3.getMatrixArray()[2][0], matrix3[2][0]);
        assertEquals(answer3.getMatrixArray()[2][1], matrix3[2][1]);
        assertEquals(answer3.getMatrixArray()[2][2], matrix3[2][2]);
    }

    @Test
    void subtraction() {
        double[] first = {1, 2, 3};
        double[] second = {4, 5, 6};
        double[] third = {7, 8, 9};
        double[][] matrix = new double[3][3];
        matrix[0] = first;
        matrix[1] = second;
        matrix[2] = third;
        Matrix3f answer = new Matrix3f(matrix);

        double[] first2 = {1, 2, 3};
        double[] second2 = {4, 5, 6};
        double[] third2 = {7, 8, 9};
        double[][] matrix2 = new double[3][3];
        matrix2[0] = first2;
        matrix2[1] = second2;
        matrix2[2] = third2;
        Matrix3f answer2 = new Matrix3f(matrix2);

        double[] first3 = {0, 0, 0};
        double[] second3 = {0, 0, 0};
        double[] third3 = {0, 0, 0};
        double[][] matrix3 = new double[3][3];
        matrix3[0] = first3;
        matrix3[1] = second3;
        matrix3[2] = third3;

        Matrix3f answer3 = Matrix3f.subtraction(answer, answer2);
        assertEquals(answer3.getMatrixArray()[0][0], matrix3[0][0]);
        assertEquals(answer3.getMatrixArray()[0][1], matrix3[0][1]);
        assertEquals(answer3.getMatrixArray()[0][2], matrix3[0][2]);
        assertEquals(answer3.getMatrixArray()[1][0], matrix3[1][0]);
        assertEquals(answer3.getMatrixArray()[1][1], matrix3[1][1]);
        assertEquals(answer3.getMatrixArray()[1][2], matrix3[1][2]);
        assertEquals(answer3.getMatrixArray()[2][0], matrix3[2][0]);
        assertEquals(answer3.getMatrixArray()[2][1], matrix3[2][1]);
        assertEquals(answer3.getMatrixArray()[2][2], matrix3[2][2]);
    }

    @Test
    void multiplierVector() {
        double[] first = {1, 2, -1};
        double[] second = {2, 3, 0};
        double[] third = {4, -2, 2};
        double[][] matrix = new double[3][3];
        matrix[0] = first;
        matrix[1] = second;
        matrix[2] = third;
        Matrix3f answer = new Matrix3f(matrix);

        Vector3f answer2 = new Vector3f(2, 1, -3);

        Vector3f answer3 = new Vector3f(7, 7, 0);

        Vector3f answer4 = Matrix3f.multiplierVector(answer, answer2);
        assertEquals(answer3.getCoordinates()[0], answer4.getCoordinates()[0]);
        assertEquals(answer3.getCoordinates()[1], answer4.getCoordinates()[1]);
        assertEquals(answer3.getCoordinates()[2], answer4.getCoordinates()[2]);
    }

    @Test
    void matrixMultiplier() {
        double[] first = {1, 2, 3};
        double[] second = {4, 5, 6};
        double[] third = {7, 8, 9};
        double[][] matrix = new double[3][3];
        matrix[0] = first;
        matrix[1] = second;
        matrix[2] = third;
        Matrix3f answer = new Matrix3f(matrix);

        double[] first2 = {2, 3, 4};
        double[] second2 = {5, 6, 7};
        double[] third2 = {8, 9, 10};
        double[][] matrix2 = new double[3][3];
        matrix2[0] = first2;
        matrix2[1] = second2;
        matrix2[2] = third2;
        Matrix3f answer2 = new Matrix3f(matrix2);

        double[] first3 = {36, 42, 48};
        double[] second3 = {81, 96, 111};
        double[] third3 = {126, 150, 174};
        double[][] matrix3 = new double[3][3];
        matrix3[0] = first3;
        matrix3[1] = second3;
        matrix3[2] = third3;

        Matrix3f answer3 = Matrix3f.matrixMultiplier(answer, answer2);
        assertEquals(answer3.getMatrixArray()[0][0], matrix3[0][0]);
        assertEquals(answer3.getMatrixArray()[0][1], matrix3[0][1]);
        assertEquals(answer3.getMatrixArray()[0][2], matrix3[0][2]);
        assertEquals(answer3.getMatrixArray()[1][0], matrix3[1][0]);
        assertEquals(answer3.getMatrixArray()[1][1], matrix3[1][1]);
        assertEquals(answer3.getMatrixArray()[1][2], matrix3[1][2]);
        assertEquals(answer3.getMatrixArray()[2][0], matrix3[2][0]);
        assertEquals(answer3.getMatrixArray()[2][1], matrix3[2][1]);
        assertEquals(answer3.getMatrixArray()[2][2], matrix3[2][2]);
    }
}