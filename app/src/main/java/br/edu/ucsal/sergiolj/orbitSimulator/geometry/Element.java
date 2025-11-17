package br.edu.ucsal.sergiolj.orbitSimulator.geometry;

import androidx.annotation.NonNull;

import br.edu.ucsal.sergiolj.orbitSimulator.util.PolarCoordinates;

public class Element {
    private int size;
    private int minSize;
    private int maxSize;

    private int color;

    private double velocity;
    
    public int getMinSize() {
        return minSize;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    private PolarCoordinates position;

    public Element(){
    }

    public int getSize() {
        return size;
    }

    public int getColor() {
        return color;
    }

    public PolarCoordinates getPosition() {
        return position;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setPosition(PolarCoordinates position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Element [size=" + size + ", minSize=" + minSize + ", maxSize=" + maxSize + ", color=" + color
                + ", velocity=" + velocity + ", position=" + position + "]";
    }
}
