package br.edu.ucsal.sergiolj.orbitSimulator.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

import br.edu.ucsal.sergiolj.orbitSimulator.geometry.Element;
import br.edu.ucsal.sergiolj.orbitSimulator.geometry.Geometry;

public class GeometryStorage {

    private static final String PREF = "geometry_prefs";

    public static void save(Context ctx, Geometry geometry) {

        SharedPreferences.Editor editor =
                ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE).edit();

        // --- GLOBAIS ---
        editor.putFloat("scaleX", (float) geometry.getScaleX());
        editor.putFloat("scaleY", (float) geometry.getScaleY());
        editor.putFloat("displacement", (float) geometry.getDisplacementSum());

        ArrayList<Element> list = geometry.getGeometrySet();
        editor.putInt("count", list.size());

        for (int i = 0; i < list.size(); i++) {

            Element e = list.get(i);

            editor.putFloat("radius_" + i, (float) e.getPosition().getRadius());
            editor.putFloat("angle_" + i, (float) e.getPosition().getAngle());

            editor.putInt("color_" + i, e.getColor());
            editor.putInt("size_" + i, e.getSize());
        }

        editor.apply();
    }


    public static void load(Context ctx, Geometry geometry) {

        SharedPreferences prefs =
                ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);

        int count = prefs.getInt("count", -1);
        if (count == -1) return;

        // GLOBAIS
        geometry.setScaleX(prefs.getFloat("scaleX", 1f));
        geometry.setScaleY(prefs.getFloat("scaleY", 1f));
        geometry.move(prefs.getFloat("displacement", 0f));

        // Elementos
        geometry.getGeometrySet().clear();

        for (int i = 0; i < count; i++) {

            Element e = new Element();

            double r = prefs.getFloat("radius_" + i, 0f);
            double a = prefs.getFloat("angle_" + i, 0f);
            int color = prefs.getInt("color_" + i, 0xFFFFFF);
            int size = prefs.getInt("size_" + i, 20);

            e.setPosition(new PolarCoordinates(r, a));
            e.setColor(color);
            e.setSize(size);

            geometry.getGeometrySet().add(e);
        }
    }
}
