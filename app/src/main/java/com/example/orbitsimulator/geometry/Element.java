package com.example.orbitsimulator.geometry;

import androidx.annotation.NonNull;

import com.example.orbitsimulator.util.PolarCoordinates;

public class Element {
    private int size;
    private int color;
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

    @NonNull
    @Override
    public String toString() {
        return String.valueOf(this.getPosition().getRadius());
    }
}
