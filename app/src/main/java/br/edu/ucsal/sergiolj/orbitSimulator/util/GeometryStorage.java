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

    // ------------------- EXTRA UI SETTINGS --------------------
public static void saveUI(Context ctx, int color, float velocity, int minSize, int maxSize) {
    SharedPreferences.Editor editor =
            ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();

    editor.putInt("ui_color", color);
    editor.putFloat("ui_velocity", velocity);
    editor.putInt("ui_minSize", minSize);
    editor.putInt("ui_maxSize", maxSize);

    editor.apply();
}

    public static UISettings loadUI(Context ctx) {
         SharedPreferences prefs =
                ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        int color = prefs.getInt("ui_color", 0xFFFFFF);
        float velocity = prefs.getFloat("ui_velocity", 0.5f);
        int minSize = prefs.getInt("ui_minSize", 10);
        int maxSize = prefs.getInt("ui_maxSize", 40);

        return new UISettings(color, velocity, minSize, maxSize);
    }
    public static class UISettings {
        public int color;
        public float velocity;
        public int minSize;
         public int maxSize;

        public UISettings(int color, float velocity, int minSize, int maxSize) {
            this.color = color;
            this.velocity = velocity;
            this.minSize = minSize;
            this.maxSize = maxSize;
        }
    }

}
