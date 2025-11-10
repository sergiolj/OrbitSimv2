package com.example.orbitsimulator.canvas;

import android.graphics.Color;
import android.graphics.Paint;


public class BrushSettings {

    private final Paint paint;

    public BrushSettings(){
        paint = new Paint();

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.LTGRAY);
        paint.setStrokeWidth(0f);
    }

    public Paint getPaint(){
        return this.paint;
    }

    /**
     * O Paint.Style é um ENUM do Paint que determina com int as três opções
     * possíveis do estilo de pintura fill(0), stroke(1) e fill e stroke (2)
     * @param style
     */
    public void setStyle(Paint.Style style){
        this.paint.setStyle(style);
    }

    /**
     * ColorPicker resulta em uma cor em formato int
     * @param color
     */
    public void setColor(int color){
        this.paint.setColor(color);
    }

    /**
     * O uso de width 0 irá criar um stroke de 1 px chamado de hairline mode
     * @param width
     */
    public void setStrokeWidth(float width){
        this.paint.setStrokeWidth(width);
    }

}
