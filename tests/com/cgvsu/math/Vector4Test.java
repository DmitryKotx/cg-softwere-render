package com.cgvsu.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyVector4Test {

    @Test
    void plusVector() {
        Vector4f first = new Vector4f(2, 4, 6, 8);
        Vector4f second = new Vector4f(2, 4, 6, 8);
        Vector4f answer = new Vector4f(4, 8, 12, 16);
        assertEquals(answer.getCoordinates()[0], Vector4f.addition(first, second).getCoordinates()[0]);
        assertEquals(answer.getCoordinates()[1], Vector4f.addition(first, second).getCoordinates()[1]);
        assertEquals(answer.getCoordinates()[2], Vector4f.addition(first, second).getCoordinates()[2]);
        assertEquals(answer.getCoordinates()[3], Vector4f.addition(first, second).getCoordinates()[3]);
    }

    @Test
    void minusVector() {
        Vector4f first = new Vector4f(2, 4, 6, 8);
        Vector4f second = new Vector4f(2, 4, 6, 8);
        Vector4f answer = new Vector4f(0, 0, 0, 0);
        assertEquals(answer.getCoordinates()[0], Vector4f.subtraction(first, second).getCoordinates()[0]);
        assertEquals(answer.getCoordinates()[1], Vector4f.subtraction(first, second).getCoordinates()[1]);
        assertEquals(answer.getCoordinates()[2], Vector4f.subtraction(first, second).getCoordinates()[2]);
        assertEquals(answer.getCoordinates()[3], Vector4f.subtraction(first, second).getCoordinates()[3]);
    }

    @Test
    void multiplierVector() {
        Vector4f first = new Vector4f(2, 4, 6, 8);
        double second = 5;
        Vector4f answer = new Vector4f(10, 20, 30, 40);
        assertEquals(answer.getCoordinates()[0], Vector4f.multiplier(first, second).getCoordinates()[0]);
        assertEquals(answer.getCoordinates()[1], Vector4f.multiplier(first, second).getCoordinates()[1]);
        assertEquals(answer.getCoordinates()[2], Vector4f.multiplier(first, second).getCoordinates()[2]);
        assertEquals(answer.getCoordinates()[3], Vector4f.multiplier(first, second).getCoordinates()[3]);
    }

    @Test
    void separationVector() {
        Vector4f first = new Vector4f(2, 4, 6, 8);
        double second = 5;
        Vector4f answer = new Vector4f(0.4, 0.8, 1.2, 1.6);
        assertEquals(answer.getCoordinates()[0], Vector4f.separation(first, second).getCoordinates()[0]);
        assertEquals(answer.getCoordinates()[1], Vector4f.separation(first, second).getCoordinates()[1]);
        assertEquals(answer.getCoordinates()[2], Vector4f.separation(first, second).getCoordinates()[2]);
        assertEquals(answer.getCoordinates()[3], Vector4f.separation(first, second).getCoordinates()[3]);
    }

    @Test
    void scalarMultiplier() {
        Vector4f first = new Vector4f(2, 4, 6, 8);
        Vector4f second = new Vector4f(2, 4, 6, 8);
        double answer = 120;
        assertEquals(answer, Vector4f.dotProduct(first, second));
    }
}