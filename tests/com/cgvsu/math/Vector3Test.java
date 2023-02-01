package com.cgvsu.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyVector3Test {

    @Test
    void plusVector() {
        Vector3f first = new Vector3f(2, 4, 6);
        Vector3f second = new Vector3f(2, 4, 6);
        Vector3f answer = new Vector3f(4, 8, 12);
        assertEquals(answer.getCoordinates()[0], Vector3f.addition(first, second).getCoordinates()[0]);
        assertEquals(answer.getCoordinates()[1], Vector3f.addition(first, second).getCoordinates()[1]);
        assertEquals(answer.getCoordinates()[2], Vector3f.addition(first, second).getCoordinates()[2]);
    }

    @Test
    void minusVector() {
        Vector3f first = new Vector3f(2, 4, 6);
        Vector3f second = new Vector3f(2, 4, 6);
        Vector3f answer = new Vector3f(0, 0, 0);
        assertEquals(answer.getCoordinates()[0], Vector3f.subtraction(first, second).getCoordinates()[0]);
        assertEquals(answer.getCoordinates()[1], Vector3f.subtraction(first, second).getCoordinates()[1]);
        assertEquals(answer.getCoordinates()[2], Vector3f.subtraction(first, second).getCoordinates()[2]);
    }

    @Test
    void multiplierVector() {
        Vector3f first = new Vector3f(2, 4, 6);
        double second = 5;
        Vector3f answer = new Vector3f(10, 20, 30);
        assertEquals(answer.getCoordinates()[0], Vector3f.multiplier(first, second).getCoordinates()[0]);
        assertEquals(answer.getCoordinates()[1], Vector3f.multiplier(first, second).getCoordinates()[1]);
        assertEquals(answer.getCoordinates()[2], Vector3f.multiplier(first, second).getCoordinates()[2]);
    }

    @Test
    void separationVector() {
        Vector3f first = new Vector3f(2, 4, 6);
        double second = 5;
        Vector3f answer = new Vector3f(0.4, 0.8, 1.2);
        assertEquals(answer.getCoordinates()[0], Vector3f.separation(first, second).getCoordinates()[0]);
        assertEquals(answer.getCoordinates()[1], Vector3f.separation(first, second).getCoordinates()[1]);
        assertEquals(answer.getCoordinates()[2], Vector3f.separation(first, second).getCoordinates()[2]);
    }

    @Test
    void scalarMultiplier() {
        Vector3f first = new Vector3f(2, 4, 6);
        Vector3f second = new Vector3f(2, 4, 6);
        double answer = 56;
        assertEquals(answer, Vector3f.dotProduct(first, second));
    }

    @Test
    void vectorsMultiplier() {
        Vector3f first = new Vector3f(2, 4, 6);
        Vector3f second = new Vector3f(4, 6, 8);
        Vector3f answer = new Vector3f(-4, 8, -4);
        assertEquals(answer.getCoordinates()[0], Vector3f.crossProduct(first, second).getCoordinates()[0]);
        assertEquals(answer.getCoordinates()[1], Vector3f.crossProduct(first, second).getCoordinates()[1]);
        assertEquals(answer.getCoordinates()[2], Vector3f.crossProduct(first, second).getCoordinates()[2]);
    }
}