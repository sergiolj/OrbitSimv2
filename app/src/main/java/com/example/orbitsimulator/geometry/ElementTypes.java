package com.example.orbitsimulator.geometry;

import com.example.orbitsimulator.util.ColorRGB;
import com.example.orbitsimulator.util.PolarCoord;

public interface ElementTypes {
    int getSize();
    ColorRGB getColor();
    PolarCoord getPosition();

    void setSize(int size);
    void setColor(ColorRGB color);
    void setPosition(PolarCoord position);

    int genSize();
}
