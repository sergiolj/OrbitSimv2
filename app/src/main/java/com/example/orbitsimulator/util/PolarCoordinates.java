package com.example.orbitsimulator.util;

public class PolarCoordinates{
    private double radius;
    private double angle;

    public PolarCoordinates(double radius, double angle) {
        this.radius = radius;
        this.angle = angle;
    }
    public PolarCoordinates(){
        this.radius = 0;
        this.angle = 0;
    }

    public PolarCoordinates setPolarCoordinates(double radius, double angle){
        this.radius = radius;
        this.angle = angle;
        return this;
    }

    public double getAngle() {
        return angle;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }


    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "PolarCoord{" +
                "radius=" + radius +
                ", angle=" + angle +
                '}';
    }
}
