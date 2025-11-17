package br.edu.ucsal.sergiolj.orbitSimulator.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

import br.edu.ucsal.sergiolj.orbitSimulator.geometry.Geometry;
import br.edu.ucsal.sergiolj.orbitSimulator.geometry.Element;
import br.edu.ucsal.sergiolj.orbitSimulator.util.PolarCoordinates;

public class GeometryStorage {

    private static final String PREF_NAME = "geometry_prefs";

    public static void saveGeometry(Context context, Geometry geometry) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putFloat("scaleX", (float) geometry.getScaleX());
        editor.putFloat("scaleY", (float) geometry.getScaleY());
        editor.putFloat("velocity", (float) geometry.getDisplacementSum());

        ArrayList<Element> list = geometry.getGeometrySet();
        editor.putInt("count", list.size());

        for (int i = 0; i < list.size(); i++) {
            Element e = list.get(i);

            PolarCoordinates p = e.getPosition();

            editor.putFloat("radius_" + i, (float) p.getRadius());
            editor.putFloat("angle_" + i, (float) p.getAngle());

            editor.putInt("r_" + i, e.getColor().getRed());
            editor.putInt("g_" + i, e.getColor().getGreen());
            editor.putInt("b_" + i, e.getColor().getBlue());

            editor.putInt("size_" + i, e.getSize());
        }

        editor.apply();
    }


    public static void loadGeometry(Context context, Geometry geometry) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        int count = prefs.getInt("count", -1);
        if (count == -1) return;

        geometry.setScaleX(prefs.getFloat("scaleX", 1f));
        geometry.setScaleY(prefs.getFloat("scaleY", 1f));
        geometry.move(prefs.getFloat("velocity", 0f));

        geometry.getGeometrySet().clear();

        for (int i = 0; i < count; i++) {
            Element e = new Element();

            float radius = prefs.getFloat("radius_" + i, 0f);
            float angle = prefs.getFloat("angle_" + i, 0f);

            int r = prefs.getInt("r_" + i, 255);
            int g = prefs.getInt("g_" + i, 255);
            int b = prefs.getInt("b_" + i, 255);

            int size = prefs.getInt("size_" + i, 20);

            e.setPosition(new PolarCoordinates(radius, angle));
            e.setColor(new java.awt.Color(r, g, b));
            e.setSize(size);

            geometry.getGeometrySet().add(e);
        }
    }
}
