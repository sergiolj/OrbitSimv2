package com.example.orbitsimulator.util;

import android.graphics.Color;
import java.util.Random;

public class ColorGenerator {

    private final Random rnd = new Random();
    private int variance = 60;

    public ColorGenerator() {}

    public int genRandColor() {
        int r = rnd.nextInt(256);
        int g = rnd.nextInt(256);
        int b = rnd.nextInt(256);

        return Color.rgb(r,g,b);
    }

    public int genColorTones(int colorBase){
        float[] hsv = new float[3];
        Color.colorToHSV(colorBase, hsv);

        float variation = this.variance / 100f;

        float newV = hsv[2] + (rnd.nextFloat() * 2f - 1f) * variation;
        hsv[2] = Math.max(0f, Math.min(1f, newV));

        return Color.HSVToColor(hsv);
    }

    public int getVariance() {
        return variance;
    }

    public void setVariance(int variance) {
        this.variance = variance;
    }
}
