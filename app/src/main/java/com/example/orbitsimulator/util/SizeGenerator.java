package com.example.orbitsimulator.util;

import java.util.Random;

public class SizeGenerator {

    private final int minSize;
    private final int maxSize;
    private final Random rnd = new Random();

    public SizeGenerator(int min, int max){
        minSize = min;
        maxSize = max;
    }

    public int genSize() {
        return rnd.nextInt((maxSize- minSize)+1)+ minSize;
    }
}
