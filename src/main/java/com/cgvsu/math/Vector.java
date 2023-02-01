package com.cgvsu.math;

public class Vector {

    private double[] coordinates;

    protected final float EQUAL_EPS = 1e-7f;

    protected Vector(){
    }

    protected Vector(int num){
        double[] coordinates =  new double[num];
        for(int i = 0; i<num; i++){
            coordinates[i] = 0;
        }
        this.coordinates = coordinates;
    }

    protected Vector(double a, double b) {
        coordinates = new double[2];
        this.coordinates[0] = a;
        this.coordinates[1] = b;
    }

    protected Vector(double a, double b, double c) {
        coordinates = new double[3];
        this.coordinates[0] = a;
        this.coordinates[1] = b;
        this.coordinates[2] = c;
    }

    protected Vector(double a, double b, double c, double d) {
        coordinates = new double[4];
        this.coordinates[0] = a;
        this.coordinates[1] = b;
        this.coordinates[2] = c;
        this.coordinates[3] = d;
    }

    protected double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Vector vector) {
        this.coordinates = vector.getCoordinates();
    }

    public double getX() {
        return coordinates[0];
    }

    public double getY() {
        return coordinates[1];
    }

    public double length() {
        double cur = 0;
        for (double coordinate : this.coordinates) {
            cur += coordinate * coordinate;
        }
        return Math.sqrt(cur);
    }

    public Vector normal() {
        int length = coordinates.length;
        new Vector(length);
        return switch (length) {
            case 2 -> new Vector(coordinates[0] / length(), coordinates[1] / length());
            case 3 -> new Vector(coordinates[0] / length(), coordinates[1] / length(), coordinates[2] / length());
            case 4 -> new Vector(coordinates[0] / length(), coordinates[1] / length(), coordinates[2] / length(), coordinates[3] / length());
            default -> new Vector(length);
        };
    }
}

