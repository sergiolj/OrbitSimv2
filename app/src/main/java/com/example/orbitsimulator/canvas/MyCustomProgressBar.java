package com.example.orbitsimulator.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyCustomProgressBar extends View {

    //Variáveis necessárias para o desenho
    private Paint backgroundPaint; // O "pincel" para o círculo cinza
    private Paint progressPaint; // O "pincel" para o arco de progresso
    private RectF rectF; // Desenho de um retângulo
    private final float strokeWidth = 20f;

    //Variável de carregamento da barra
    private int progress = 0;


    public MyCustomProgressBar(Context context) {
        super(context);
        init(null);
    }

    public MyCustomProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MyCustomProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        // Criando o pincel
        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(Color.parseColor("#E0E0E0")); // Cinza claro
        backgroundPaint.setStyle(Paint.Style.FILL); // Apenas o contorno
        backgroundPaint.setStrokeWidth(strokeWidth);

        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setColor(Color.parseColor("#FF6200EE")); // Roxo (Cor primária)
        progressPaint.setStyle(Paint.Style.FILL);
        //progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(strokeWidth);
        progressPaint.setStrokeCap(Paint.Cap.ROUND); // Pontas do arco arredondadas

        rectF = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = Math.min(width,height);

        // setMeasuredDimension() é obrigatório. É como você "avisa" o Android sobre o seu tamanho final.
        setMeasuredDimension(size,size);
    }

    //Esse metodo define como os filhos adicionados a essa view serão posicionados, como
    //não temos filhos nesse caso o método não precisa ser sobreescrito
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        // 1. Calcula o centro (X, Y) e o raio final (já descontando a linha)
        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;
        float radius = (Math.min(getWidth(), getHeight()) / 2f) - (strokeWidth / 2f);

        // 2. Define o retângulo do arco (lembre-se que 'rectF' é uma variável da classe)
        rectF.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);

        canvas.drawCircle(centerX, centerY, radius, backgroundPaint);

        float sweepAngle = (progress/100f)*360f;

        canvas.drawArc(rectF, -90, sweepAngle, true, progressPaint);
        //canvas.drawArc(rectF, -90, sweepAngle, false, progressPaint);
    }

    public void setProgress(int newProgress) {
        if(newProgress < 0){
            progress = 0;
        }else if (newProgress >100){
            progress = 100;
        }else {
            progress = newProgress;
        }
        // IMPORTANTE:
        // Chama o invalidate() para forçar o Android
        // a chamar o onDraw() novamente.
        invalidate();
    }

    public int getProgress() {
        return progress;
    }

}
