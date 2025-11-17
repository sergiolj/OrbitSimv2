package com.example.orbitsimulator.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.orbitsimulator.geometry.ElementTypes;
import com.example.orbitsimulator.geometry.Geometry;
import com.example.orbitsimulator.util.PolarCoord;

import java.util.ArrayList;

public class GeometryStorage {

    private static final String PREF_NAME = "geometry_prefs";

    public static void saveGeometry(Context context, Geometry geometry) {

        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // --- SALVA PARAMETROS GERAIS ---
        editor.putFloat("scaleX", (float) geometry.getScaleX());
        editor.putFloat("scaleY", (float) geometry.getScaleY());
        editor.putFloat("displacementSum", (float) geometry.getDisplacementSum());

        // COR BASE
        ColorRGB base = geometry.getBasePalette();
        editor.putInt("baseR", base.getR());
        editor.putInt("baseG", base.getG());
        editor.putInt("baseB", base.getB());

        // --- SALVAR ELEMENTOS ---
        ArrayList<ElementTypes> list = geometry.getGeometrySet();
        editor.putInt("count", list.size());

        for (int i = 0; i < list.size(); i++) {
            ElementTypes e = list.get(i);

            PolarCoord p = e.getPosition();
            ColorRGB c = e.getColor();

            editor.putFloat("radius_" + i, (float) p.getRadius());
            editor.putFloat("angle_" + i, (float) p.getAngle());

            editor.putInt("r_" + i, c.getR());
            editor.putInt("g_" + i, c.getG());
            editor.putInt("b_" + i, c.getB());

            editor.putInt("size_" + i, e.getSize());
        }

        editor.apply();
    }


    public static void loadGeometry(Context context, Geometry geometry) {

        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // --- RETORNA SE NÃO EXISTE NADA ---
        int count = prefs.getInt("count", -1);
        if (count == -1) return; // nada salvo ainda

        // --- CARREGA PARAMETROS GERAIS ---
        geometry.setScaleX(prefs.getFloat("scaleX", 1f));
        geometry.setScaleY(prefs.getFloat("scaleY", 1f));
        geometry.move(prefs.getFloat("displacementSum", 0f));

        // COR BASE
        int baseR = prefs.getInt("baseR", 0);
        int baseG = prefs.getInt("baseG", 0);
        int baseB = prefs.getInt("baseB", 0);
        geometry.setBasePalette(new ColorRGB(baseR, baseG, baseB));

        // --- LIMPA GEOMETRIA ATUAL ---
        geometry.getGeometrySet().clear();

        // --- RECRIAR ELEMENTOS ---
        for (int i = 0; i < count; i++) {

            ElementTypes e = geometry.getElementFactory().get();

            float radius = prefs.getFloat("radius_" + i, 0f);
            float angle = prefs.getFloat("angle_" + i, 0f);

            int r = prefs.getInt("r_" + i, 255);
            int g = prefs.getInt("g_" + i, 255);
            int b = prefs.getInt("b_" + i, 255);

            int size = prefs.getInt("size_" + i, 20);

            e.setPosition(new PolarCoord(radius, angle));
            e.setColor(new ColorRGB(r, g, b));
            e.setSize(size);

            geometry.getGeometrySet().add(e);
        }
    }
}
