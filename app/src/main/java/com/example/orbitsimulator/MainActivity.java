package com.example.orbitsimulator;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.orbitsimulator.canvas.GeometryCanvas;
import com.example.orbitsimulator.geometry.Geometry;
import com.example.orbitsimulator.geometry.SolidCircle;
import com.example.orbitsimulator.util.ColorRGB;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Geometry geometry;
    private boolean spinning = false;
    private Handler handler;
    private SeekBar horizontalScale;
    private SeekBar verticalScale;
    private SeekBar rotationVelocity;
    private GeometryCanvas canva;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        handler = new Handler(Looper.getMainLooper());

        // Ligação das referências dos atributos na Activity
        canva = findViewById(R.id.geometryCanvas);

        horizontalScale = findViewById(R.id.sb_horizontalscale);
        horizontalScale.setMin(1);
        verticalScale = findViewById(R.id.sb_verticalscale);
        verticalScale.setMin(1);
        rotationVelocity = findViewById(R.id.sb_rotationvelocity);
        rotationVelocity.setMin(0);

        Button btnStartBoost = findViewById(R.id.btn_start);
        Button btnStop = findViewById(R.id.btn_stop);
        Button btnExit = findViewById(R.id.btn_exit);

        //Criação dos Arrays com os dados de geometria
        geometry = Geometry.getInstance();

        geometry.setBasePalette(new ColorRGB(50,100,200));
        //Uso de method reference para passar uma Supplier, ou seja uma fábrica de uma determinada classe.

        geometry.populateGeometrySet(SolidCircle::new);
        geometry.orbitTraceGeometry();

        ArrayList<Double> orbit = geometry.orbitTraceGeometry();
        String TAG = "Debug geometria Orbit";
        Log.d(TAG, orbit.toString());
        scaleWatcher();
        beginTimer();

        btnStartBoost.setOnClickListener(v ->{
                spinning =  true;
                beginTimer();
        });
        btnStop.setOnClickListener(v -> spinning=false);

        btnExit.setOnClickListener(v -> {
            if(handler != null){
                handler.removeCallbacksAndMessages(null);
            }
            finishAffinity();
            //finish();
        });

    }

    private void scaleWatcher() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        spinning = false;
        if(handler != null){
            handler.removeCallbacksAndMessages(null);
        }
    }

    private void beginTimer() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                geometry.boost((float) rotationVelocity.getProgress() / 10);
                geometry.setScaleX((float) horizontalScale.getProgress() / 50);
                geometry.setScaleY((float) verticalScale.getProgress() / 50);
                if (spinning) {
                    handler.postDelayed(this, 15);
                }
                canva.updateImage();

            }
        });
    }
}