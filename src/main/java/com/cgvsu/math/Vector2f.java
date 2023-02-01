package com.cgvsu.math;

public class Vector2f extends Vector {

    public Vector2f(double a, double b) {
        super(a, b);
    }

    public Vector2f() {
        super(2);
    }

    public static Vector2f addition(Vector2f v1, Vector2f v2) {
        return new Vector2f(v1.getCoordinates()[0] + v2.getCoordinates()[0], v1.getCoordinates()[1] + v2.getCoordinates()[1]);
    }

    public static Vector2f subtraction(Vector2f v1, Vector2f v2) {
        return new Vector2f(v1.getCoordinates()[0] - v2.getCoordinates()[0], v1.getCoordinates()[1] - v2.getCoordinates()[1]);
    }

    public static Vector2f multiplierVector(Vector2f v, double n) {
        return new Vector2f(v.getCoordinates()[0] * n, v.getCoordinates()[1] * n);
    }

    public static Vector2f separationVector(Vector2f v, double n) {
        return new Vector2f(v.getCoordinates()[0] / n, v.getCoordinates()[1] / n);
    }

    public static double scalarMultiplier(Vector2f v1, Vector2f v2) {
        return v1.getCoordinates()[0] * v2.getCoordinates()[0] + v1.getCoordinates()[1] * v2.getCoordinates()[1];
    }

    public void normalize() {
      Vector2f vector = new Vector2f(super.getX() / length(), super.getY() / length());
      super.setCoordinates(vector);
    }
}
