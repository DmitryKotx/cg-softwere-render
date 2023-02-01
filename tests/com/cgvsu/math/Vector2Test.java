package com.cgvsu.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyVector2Test {

    @Test
    void plusVector() {
        Vector2f first = new Vector2f(2, 4);
        Vector2f second = new Vector2f(2, 4);
        Vector2f answer = new Vector2f(4, 8);
        assertEquals(answer.getCoordinates()[0], Vector2f.addition(first, second).getCoordinates()[0]);
        assertEquals(answer.getCoordinates()[1], Vector2f.addition(first, second).getCoordinates()[1]);
    }

    @Test
    void minusVector() {
        Vector2f first = new Vector2f(2, 4);
        Vector2f second = new Vector2f(2, 4);
        Vector2f answer = new Vector2f(0, 0);
        assertEquals(answer.getCoordinates()[0], Vector2f.subtraction(first, second).getCoordinates()[0]);
        assertEquals(answer.getCoordinates()[1], Vector2f.subtraction(first, second).getCoordinates()[1]);
    }

    @Test
    void multiplierVector() {
        Vector2f first = new Vector2f(2, 4);
        double second = 5;
        Vector2f answer = new Vector2f(10, 20);
        assertEquals(answer.getCoordinates()[0], Vector2f.multiplierVector(first, second).getCoordinates()[0]);
        assertEquals(answer.getCoordinates()[1], Vector2f.multiplierVector(first, second).getCoordinates()[1]);
    }

    @Test
    void separationVector() {
        Vector2f first = new Vector2f(2, 4);
        double second = 5;
        Vector2f answer = new Vector2f(0.4, 0.8);
        assertEquals(answer.getCoordinates()[0], Vector2f.separationVector(first, second).getCoordinates()[0]);
        assertEquals(answer.getCoordinates()[1], Vector2f.separationVector(first, second).getCoordinates()[1]);
    }

    @Test
    void scalarMultiplier() {
        Vector2f first = new Vector2f(2, 4);
        Vector2f second = new Vector2f(2, 4);
        double answer = 20;
        assertEquals(answer, Vector2f.scalarMultiplier(first, second));
    }
}