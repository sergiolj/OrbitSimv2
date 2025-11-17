package com.example.orbitsimulator.util;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.orbitsimulator.geometry.Geometry;  
import com.example.orbitsimulator.util.ColorRGB;

public class GeometryStorage{
private static final String PREF_NAME = "geometry_prefs";

    public static void saveGeometry(Context context, double scaleX, double scaleY, double displacementSum, ColorRGB basePalette) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putFloat("scaleX", (float) scaleX);
        editor.putFloat("scaleY", (float) scaleY);
        editor.putFloat("displacementSum", (float) displacementSum);
        editor.putInt("baseColorR", basePalette.getR());
        editor.putInt("baseColorG", basePalette.getG());
        editor.putInt("baseColorB", basePalette.getB());

        editor.apply();
    }

    public static void loadGeometry(Context context, Geometry geometry) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        geometry.setScaleX(prefs.getFloat("scaleX", 1.0f));
        geometry.setScaleY(prefs.getFloat("scaleY", 1.0f));
        geometry.move(prefs.getFloat("displacementSum", 0f));


        int r = prefs.getInt("baseColorR", 50);
        int g = prefs.getInt("baseColorG", 100);
        int b = prefs.getInt("baseColorB", 200);
        geometry.setBasePalette(new ColorRGB(r, g, b));
    }
}