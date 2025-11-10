package com.example.orbitsimulator.geometry;

import com.example.orbitsimulator.util.ColorRGB;
import com.example.orbitsimulator.util.PolarCoord;

public interface ElementTypes {
    int getSize();
    void setSize(int size);
    ColorRGB getColor();
    void setColor(ColorRGB color);
    PolarCoord getPosition();
    void setPosition(PolarCoord position);

    int genSize();
}
