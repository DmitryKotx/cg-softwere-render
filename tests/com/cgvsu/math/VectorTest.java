package com.cgvsu.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyVectorTest {

    @Test
    void getCoords() {
        Vector3f myVector3 = new Vector3f(4, 6, 8);
        double[] answer = {4, 6, 8};
        assertEquals(answer[0], myVector3.getCoordinates()[0]);
        assertEquals(answer[1], myVector3.getCoordinates()[1]);
        assertEquals(answer[2], myVector3.getCoordinates()[2]);
    }

    @Test
    void length() {
        Vector4f myVector4 = new Vector4f(4, 6, 8, 1);
        float ans = (float) 10.82;
        int result = (int)Math.round(myVector4.length()*100);
        float result2 = (float) result / 100;
        assertEquals(ans, result2);
    }

    @Test
    void normal() {
        Vector2f myVector2 = new Vector2f(4, 3);
        Vector ans = new Vector(0.8, 0.6);
        assertEquals(myVector2.normal().getCoordinates()[0], ans.getCoordinates()[0]);
        assertEquals(myVector2.normal().getCoordinates()[1], ans.getCoordinates()[1]);
    }
}