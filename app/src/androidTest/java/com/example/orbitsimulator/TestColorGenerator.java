package com.example.orbitsimulator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import android.graphics.Color;

import com.example.orbitsimulator.util.ColorGenerator;

public class TestColorGenerator {


    @Test
    public void TessRandomGeneratorMethod(){
        ColorGenerator cg = new ColorGenerator();

        for(int i=0; i<100; i++){
            int randColor = cg.genRandColor();

            int r = Color.red(randColor);
            int g = Color.green(randColor);
            int b = Color.blue(randColor);

            int alpha = Color.alpha(randColor);

            assertEquals("Alpha deve ser sempre 255", 255, alpha);
            assertTrue("Vermelho fora do intervalo: " + r, r >=0 && r <=255);
            assertTrue("Verde fora do intervalo: " + g, g >=0 && g <=255);
            assertTrue("Azul fora do intervalo: " + b, b >=0 && b <=255);
        }

    }
}
