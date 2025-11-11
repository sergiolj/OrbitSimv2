package com.example.orbitsimulator.geometry;

import androidx.annotation.NonNull;

import com.example.orbitsimulator.util.ColorRGB;
import com.example.orbitsimulator.util.PolarCoord;

import java.util.Random;

public class ElementCircle implements ElementTypes {
    private int size;
    private ColorRGB color;
    private PolarCoord position;
    private final int MIN_SIZE = 5;
    private final int MAX_SIZE = 25;
    private final Random rnd = new Random();

    public ElementCircle(){
        this.color = new ColorRGB();
        this.position = new PolarCoord();
        this.size = genSize(); // default
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ColorRGB getColor() {
        return color;
    }

    public void setColor(ColorRGB color) {
        this.color = color;
    }

    public PolarCoord getPosition() {
        return position;
    }

    public void setPosition(PolarCoord position) {
        this.position = position;
    }

    @Override
    public int genSize() {
        return rnd.nextInt((MAX_SIZE-MIN_SIZE)+1)+MIN_SIZE;
    }

    @NonNull
    @Override
    public String toString() {
        return String.valueOf(this.getPosition().getRadius());
    }
}
