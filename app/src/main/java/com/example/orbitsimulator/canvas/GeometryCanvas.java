package com.example.orbitsimulator.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.orbitsimulator.geometry.Element;
import com.example.orbitsimulator.geometry.Geometry;

import java.util.ArrayList;

public class GeometryCanvas extends View {
    private float xCenterOfView;
    private float yCenterOfView;

    private int viewWidth;
    private int viewHeight;

    private Paint paintOrbit;
    private Paint paintElements;
    private Geometry geometry;


    public GeometryCanvas(Context context) {
        super(context);
    }

    public GeometryCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.paintOrbit = new Paint();
        this.paintOrbit.setColor(Color.LTGRAY);
        this.paintOrbit.setStyle(Paint.Style.STROKE);
        this.paintOrbit.setStrokeWidth(0);

        this.paintElements = new Paint();
        this.paintElements.setStyle(Paint.Style.FILL_AND_STROKE);

        this.geometry = Geometry.getInstance();
    }


    /**
     * O onMeasure não é necessário porque o GeometryCanvas não define seu próprio tamanho,
     * ele se adapta ao tamanho definido para ele pelo Layout a qual foi inserido.
     *
     * @param widthMeasureSpec horizontal space requirements as imposed by the parent.
     *                         The requirements are encoded with
     *                         {@link android.view.View.MeasureSpec}.
     * @param heightMeasureSpec vertical space requirements as imposed by the parent.
     *                         The requirements are encoded with
     *                         {@link android.view.View.MeasureSpec}.
     *
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.viewWidth = w;
        this.viewHeight = h;
        invalidate();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);
        geometry.updateGeometrySet();

        this.xCenterOfView = viewWidth / 2f;
        this.yCenterOfView = viewHeight / 2f;
        drawOrbits(canvas);
        drawDataGeometry(canvas);
    }

    private void drawOrbits(Canvas canvas) {

        float sx = (float) geometry.getScaleX();
        float sy = (float) geometry.getScaleY();

        canvas.save();
        canvas.scale(sx,sy, xCenterOfView, yCenterOfView);
        float r;
        ArrayList<Double> orbit = geometry.getOrbitRadius();
        for (Double element : orbit) {
            r = (float) element.doubleValue();
            canvas.drawCircle(xCenterOfView,yCenterOfView,r,paintOrbit);
        }
        canvas.restore();
    }

    private void drawDataGeometry(Canvas canvas) {
        float x;
        float y;
        float r;
        float teta;

        float sx = (float) geometry.getScaleX();
        float sy = (float) geometry.getScaleY();
        for (Element element : geometry.getGeometrySet()) {
            r = (float) element.getPosition().getRadius();
            teta = (float) element.getPosition().getAngle();

            /**
             * Podemos dar a possibilidade ao usuário de modificar o centro da geometria para
             * ficar fora do centro da view?
             */
            x = (float) (xCenterOfView + sx * r * Math.cos(teta));
            y = (float) (yCenterOfView - sy * r * Math.sin(teta));

            paintElements.setColor(element.getColor());
            Log.d("DEBUG ALPHA", "Paint alpha = " + paintElements.getAlpha());
            Log.d("DEBUG COLOR", "Paint color = " + paintElements.getColor());

            canvas.drawCircle(x, y, (float) element.getSize(), paintElements);
       }
    }

    @Deprecated
    public void drawRectangles(Canvas canvas){
        float x;
        float y;
        float r;
        float teta;

        float sx = (float) geometry.getScaleX();
        float sy = (float) geometry.getScaleY();
        for (Element element : geometry.getGeometrySet()) {
            r = (float) element.getPosition().getRadius();
            teta = (float) element.getPosition().getAngle();

            /**
             * Podemos dar a possibilidade ao usuário de modificar o centro da geometria para
             * ficar fora do centro da view?
             */
            x = (float) (xCenterOfView + sx * r * Math.cos(teta));
            y = (float) (yCenterOfView - sy * r * Math.sin(teta));


            float size = element.getSize();
            float coordLeftX = x - (size * sx);
            float coordTopY = y - (size * sy);
            float coordRightX = x + (size * sx);
            float coordBottomY = y + (size * sy);

            /**
             * A definição como ponto central do início das coordenadas
             * Os pares de coordenadas para desenhar o retângulo são dados pelos pares (left,top) e
             * (right, bottom) onde top e bottom representam as coordenadas Y no sistema cartesiano
             * e left e right as coordenadas X, assim definindo todos os 4 eixos necessários à
             * representação das arestas.
             * O ponto 0,0 é determinado pelo centro
             */
            paintElements.setColor(element.getColor());
            canvas.drawRect(coordLeftX, coordTopY, coordRightX, coordBottomY, paintElements);
        }
    }

    public void updateImage() {
        invalidate();
    }
}
