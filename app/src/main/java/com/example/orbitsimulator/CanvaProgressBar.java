package com.example.orbitsimulator;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.orbitsimulator.canvas.MyCustomProgressBar;
import com.google.android.material.slider.Slider;

public class CanvaProgressBar extends AppCompatActivity {

    private MyCustomProgressBar circularProgressBar;
    private Slider slider;
    private TextView txSliderValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canva_progress_bar);

       circularProgressBar = findViewById(R.id.custom_progress_bar_view);
       slider = findViewById(R.id.sl_progress);
       txSliderValue = findViewById(R.id.tx_slide_value);

       slider.addOnChangeListener(new Slider.OnChangeListener() {
           @Override
           public void onValueChange(@NonNull Slider slider, float v, boolean b) {
                circularProgressBar.setProgress((int) v);
                txSliderValue.setText(String.valueOf(v));
           }
       });

       circularProgressBar.setProgress(0);
       slider.setValue(0);

    }
}