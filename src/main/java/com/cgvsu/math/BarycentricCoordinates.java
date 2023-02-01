package com.cgvsu.math;

public class BarycentricCoordinates {

    private final double u;
    private final double v;
    private final double w;

    public BarycentricCoordinates(Vector2f a, Vector2f b, Vector2f c, Vector2f p) {
        double s = (b.getX() - a.getX()) * (c.getY() - a.getY()) -
                (c.getX() - a.getX()) * (b.getY() - a.getY());
        double s1 = (b.getX() - p.getX()) * (c.getY() - p.getY()) -
                (c.getX() - p.getX()) * (b.getY() - p.getY());
        double s2 = (c.getX() - p.getX()) * (a.getY() - p.getY()) -
                (a.getX() - p.getX()) * (c.getY() - p.getY());
        u = s1 / s;
        v = s2 / s;
        w = 1 - u - v;
    }

    public double getU() {
        return u;
    }

    public double getV() {
        return v;
    }

    public double getW() {
        return w;
    }

    public boolean belongsToTriangle() {
        return u >= 0 && u <= 1 && v >= 0 && v <= 1 && w >= 0 && w <= 1;
    }
}