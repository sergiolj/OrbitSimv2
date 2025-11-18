package br.edu.ucsal.sergiolj.orbitSimulator.util;

import android.content.Context;
import android.content.SharedPreferences;

public class UIStorage {

    private static final String PREF = "ui_preferences";

    // ----- SALVAR -----
    public static void save(
            Context context,
            float scaleX,
            float scaleY,
            int rotationSpeed,
            int minSize,
            int maxSize,
            int color
    ) {
        SharedPreferences prefs = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        prefs.edit()
                .putFloat("scaleX", scaleX)
                .putFloat("scaleY", scaleY)
                .putInt("rotationSpeed", rotationSpeed)
                .putInt("minSize", minSize)
                .putInt("maxSize", maxSize)
                .putInt("color", color)
                .apply();
    }

    // ----- RESTAURAR -----
    public static float getScaleX(Context c, float def) {
        return c.getSharedPreferences(PREF, Context.MODE_PRIVATE).getFloat("scaleX", def);
    }

    public static float getScaleY(Context c, float def) {
        return c.getSharedPreferences(PREF, Context.MODE_PRIVATE).getFloat("scaleY", def);
    }

    public static int getRotationSpeed(Context c, int def) {
        return c.getSharedPreferences(PREF, Context.MODE_PRIVATE).getInt("rotationSpeed", def);
    }

    public static int getMinSize(Context c, int def) {
        return c.getSharedPreferences(PREF, Context.MODE_PRIVATE).getInt("minSize", def);
    }

    public static int getMaxSize(Context c, int def) {
        return c.getSharedPreferences(PREF, Context.MODE_PRIVATE).getInt("maxSize", def);
    }

    public static int getColor(Context c, int def) {
        return c.getSharedPreferences(PREF, Context.MODE_PRIVATE).getInt("color", def);
    }
}
