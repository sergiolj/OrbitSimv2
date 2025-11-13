package com.example.orbitsimulator.geometry;

import androidx.annotation.NonNull;

import com.example.orbitsimulator.util.ColorRGB;
import com.example.orbitsimulator.util.PolarCoord;

import java.util.Random;

public class Element {
    private int size;
    private ColorRGB color;

    private PolarCoord position;
    private int minSize = 5;
    private int maxSize = 25;
    private final Random rnd = new Random();

    //CONSTRUCTOR
    public Element(){
        this.color = new ColorRGB();
        this.position = new PolarCoord();
        this.size = genSize(); // default
    }

    //GETTERS
    public int getSize() {
        return size;
    }

    public ColorRGB getColor() {
        return color;
    }

    public PolarCoord getPosition() {
        return position;
    }
    

    //SETTERS
    public void setSize(int size) {
        this.size = size;
    }

    public void setMinSize(int size) {
        this.minSize = size;
    }

    public void setMaxSize(int size) {
        this.maxSize = size;
    }
    public void setColor(ColorRGB color) {
        this.color = color;
    }

    public void setPosition(PolarCoord position) {
        this.position = position;
    }

    //METHODS
    public int genSize() {
        return rnd.nextInt((maxSize- minSize)+1)+ minSize;
    }

    @NonNull
    @Override
    public String toString() {
        return String.valueOf(this.getPosition().getRadius());
    }
}
