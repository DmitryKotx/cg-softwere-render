package com.cgvsu.math;

public class Vector4f extends Vector {

    public Vector4f(double a, double b, double c, double d) {
        super(a, b, c, d);
    }

    public Vector4f() {
        super(4);
    }

    public double getZ() {
        return getCoordinates()[2];
    }

    public double getW() {
        return getCoordinates()[3];
    }

    public static Vector4f addition(Vector4f v1, Vector4f v2) {
        return new Vector4f(v1.getCoordinates()[0] + v2.getCoordinates()[0], v1.getCoordinates()[1] + v2.getCoordinates()[1], v1.getCoordinates()[2] + v2.getCoordinates()[2], v1.getCoordinates()[3] + v2.getCoordinates()[3]);
    }

    public static Vector4f subtraction(Vector4f v1, Vector4f v2) {
        return new Vector4f(v1.getCoordinates()[0] - v2.getCoordinates()[0], v1.getCoordinates()[1] - v2.getCoordinates()[1], v1.getCoordinates()[2] - v2.getCoordinates()[2], v1.getCoordinates()[3] - v2.getCoordinates()[3]);
    }

    public static Vector4f multiplier(Vector4f v, double n) {
        return new Vector4f(v.getCoordinates()[0] * n, v.getCoordinates()[1] * n, v.getCoordinates()[2] * n, v.getCoordinates()[3] * n);
    }

    public static Vector4f separation(Vector4f v, double n) {
        return new Vector4f(v.getCoordinates()[0] / n, v.getCoordinates()[1] / n, v.getCoordinates()[2] / n, v.getCoordinates()[3] / n);
    }

    public static double dotProduct(Vector4f v1, Vector4f v2) {
        return v1.getCoordinates()[0] * v2.getCoordinates()[0] + v1.getCoordinates()[1] * v2.getCoordinates()[1] + v1.getCoordinates()[2] * v2.getCoordinates()[2] + v1.getCoordinates()[3] * v2.getCoordinates()[3];
    }

    public void normalize() {
        Vector4f vector = new Vector4f(super.getX() / length(), super.getY() / length(), getZ() / length(), getW() / length());
        super.setCoordinates(vector);
    }
}
