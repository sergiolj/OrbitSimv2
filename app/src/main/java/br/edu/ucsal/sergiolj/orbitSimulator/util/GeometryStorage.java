package br.edu.ucsal.sergiolj.orbitSimulator.util;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.edu.ucsal.sergiolj.orbitSimulator.geometry.Element;
import br.edu.ucsal.sergiolj.orbitSimulator.geometry.Geometry;
import br.edu.ucsal.sergiolj.orbitSimulator.util.PolarCoordinates;

public class GeometryStorage {

    private static final String PREF = "geometry_save";
    private static final String KEY_GEOMETRY = "geometry_json";
    private static final String KEY_UI = "ui_settings";

    // -------------------------------------------------------------
    //  SAVE GEOMETRY (ALL ELEMENTS, POSITIONS, COLORS, SIZES, ETC)
    // -------------------------------------------------------------
    public static void saveGeometry(Context ctx, Geometry geometry) {
        try {
            JSONObject obj = geometry.toJson();
            SharedPreferences sp = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
            sp.edit().putString(KEY_GEOMETRY, obj.toString()).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------------------------------------------------
    //  LOAD GEOMETRY
    // -------------------------------------------------------------
    public static void load(Context ctx, Geometry geometry) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        String json = sp.getString(KEY_GEOMETRY, null);

        if (json == null) {
            return; // nada salvo ainda
        }

        try {
            JSONObject obj = new JSONObject(json);
            geometry.fromJson(obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // -------------------------------------------------------------
    //  UI SETTINGS
    // -------------------------------------------------------------
    public static class UISettings {
        public int color = 0xFFFFFFFF;
        public float velocity = 1f;
        public int minSize = 5;
        public int maxSize = 25;
    }

    public static void saveUI(
            Context ctx,
            int color,
            float velocity,
            int minSize,
            int maxSize
    ) {
        try {
            JSONObject ui = new JSONObject();
            ui.put("color", color);
            ui.put("velocity", velocity);
            ui.put("minSize", minSize);
            ui.put("maxSize", maxSize);

            SharedPreferences sp = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
            sp.edit().putString(KEY_UI, ui.toString()).apply();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static UISettings loadUI(Context ctx) {
        UISettings ui = new UISettings();

        SharedPreferences sp = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        String json = sp.getString(KEY_UI, null);

        if (json == null) return ui;

        try {
            JSONObject obj = new JSONObject(json);

            ui.color = obj.getInt("color");
            ui.velocity = (float) obj.getDouble("velocity");
            ui.minSize = obj.getInt("minSize");
            ui.maxSize = obj.getInt("maxSize");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ui;
    }
}
