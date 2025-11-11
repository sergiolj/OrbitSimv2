package com.example.orbitsimulator.util;

import androidx.annotation.NonNull;

import java.util.Random;

public class ColorRGB {
    private final int r;
    private final int g;
    private final int b;

    private final Random rnd = new Random();
    private ColorRGB BASE_COLOR_PALETTE = null;
    private int variance = 25;

    public ColorRGB(){
        this.r = 0;
        this.g = 0;
        this.b = 0;
    }

    public ColorRGB(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public ColorRGB(int r, int g, int b, int variance) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.variance = variance;
        this.BASE_COLOR_PALETTE = new ColorRGB(r,g,b,variance);
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    public ColorRGB genColor(ColorRGB colorBase, int variance) {
        ColorRGB colorRandom;
        int r = randNear(colorBase.getR(), variance);
        int g = randNear(colorBase.getG(), variance);
        int b = randNear(colorBase.getB(), variance);
        return colorRandom = new ColorRGB(r,g,b);
    }

    private int randNear(int baseColor, int variance) {
        int min = Math.max(0, baseColor - variance);
        int max = Math.min(255, baseColor + variance);
        return rnd.nextInt((max - min) + 1) + min;
    }

    /**
     * O format() com o valor X ou x formata o inteiro indicado em Hexadecimal
     *  "%02X" significa:
     *            "X" = Hexadecimal Maiúsculo
     *            "2" = Ter no mínimo 2 dígitos
     *            "0" = Preencher com zeros à esquerda se for menor que 2 dígitos
     * @return valor Hexadecinal
     */
    @NonNull
    @Override
    public String toString() {
        return String.format("#%02X%02X%02X", r, g, b);
    }


}
