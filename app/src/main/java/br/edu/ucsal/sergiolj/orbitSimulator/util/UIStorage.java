package br.edu.ucsal.sergiolj.orbitSimulator.util;

import android.content.Context;
import android.content.SharedPreferences;

public class UIStorage {

    private static final String PREF = "ui_state";

    public static void saveUI(
            Context context,
            int sphereColor,
            int speed,
            int horizontality,
            int verticality,
            int minSize,
            int maxSize
    ) {
        SharedPreferences prefs = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);

        prefs.edit()
                .putInt("sphereColor", sphereColor)
                .putInt("speed", speed)
                .putInt("horizontality", horizontality)
                .putInt("verticality", verticality)
                .putInt("minSize", minSize)
                .putInt("maxSize", maxSize)
                .apply();
    }

    public static int getSphereColor(Context c, int def) {
        return c.getSharedPreferences(PREF, Context.MODE_PRIVATE).getInt("sphereColor", def);
    }

    public static int getSpeed(Context c, int def) {
        return c.getSharedPreferences(PREF, Context.MODE_PRIVATE).getInt("speed", def);
    }

    public static int getHorizontality(Context c, int def) {
        return c.getSharedPreferences(PREF, Context.MODE_PRIVATE).getInt("horizontality", def);
    }

    public static int getVerticality(Context c, int def) {
        return c.getSharedPreferences(PREF, Context.MODE_PRIVATE).getInt("verticality", def);
    }

    public static int getMinSize(Context c, int def) {
        return c.getSharedPreferences(PREF, Context.MODE_PRIVATE).getInt("minSize", def);
    }

    public static int getMaxSize(Context c, int def) {
        return c.getSharedPreferences(PREF, Context.MODE_PRIVATE).getInt("maxSize", def);
    }
}
