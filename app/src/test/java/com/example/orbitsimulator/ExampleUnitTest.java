package com.example.orbitsimulator;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.orbitsimulator.util.ColorRGB;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testDefaultConstructorColorRGB(){
        ColorRGB color = new ColorRGB();
        String expectedHex = "#000000";
        assertEquals(expectedHex, color.toString());
    }

    @Test
    public void testParametrizedConstructorColorRGBBlue(){
        ColorRGB color = new ColorRGB(0,0,255);
        String expectedHex = "#0000FF";
        assertEquals(expectedHex,color.toString());
    }
}