package com.example.orbitsimulator;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.orbitsimulator.canvas.GeometryCanvas;
import com.example.orbitsimulator.geometry.Geometry;
import com.example.orbitsimulator.geometry.ElementCircle;
import com.example.orbitsimulator.util.ColorRGB;

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

        interfaceInitializer();
        dataInitializer();
        animationControl();

    }

    /**
     * INICIALIZA AS FUNCIONALIDADES DE CONTROLES DA INTERFACE COM O USUÁRIO
     *
     */
    private void interfaceInitializer() {
        Button btnStartBoost = findViewById(R.id.btn_start);
        Button btnStop = findViewById(R.id.btn_stop);
        Button btnExit = findViewById(R.id.btn_exit);

        btnStartBoost.setOnClickListener(v ->{
            spinning =  true;
            animationControl();
        });
        btnStop.setOnClickListener(v -> spinning=false);

        btnExit.setOnClickListener(v -> {
            if(handler != null){
                handler.removeCallbacksAndMessages(null);
            }
            finish();
        });

        scaleWatcher();
    }

    /**
     * INICIALIZA AS REFERÊNCIAS DOS DADOS GEOMÉTRICOS A SEREM UTILIZADOS NO SISTEMA
     */
    private void dataInitializer() {
        canva = findViewById(R.id.geometryCanvas);

        geometry = Geometry.getInstance();
        geometry.setBasePalette(new ColorRGB(50,100,200));

        //Uso de method reference para passar uma Supplier, ou seja uma fábrica de uma determinada classe.
        geometry.populateGeometrySet(ElementCircle::new);
        geometry.orbitTraceGeometry();
    }


    /**
     * INICIALIZA A THREAD QUE CONTROLA A ANIMAÇÃO/ ATUALIZAÇÃO DAS POSIÇÕES DAS ÓRBITAS
     */
    private void animationControl() {
        handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                geometry.move((float) rotationVelocity.getProgress() / 10);
                if (spinning) {
                    handler.postDelayed(this, 15);
                }
                canva.updateImage();
            }
        });
    }

    /**
     * IMPLEMENTAÇÃO DE LISTENER PARA OS SEEKBARS
     * Diferentemente do botão que é uma interface com apenas um método implementado
     * (interface funcional) o seekbar possui três métodos (interface não-funcional), o que impede o
     * uso de lambda para a implementação, pois todos os métodos da classe
     * tem que ser sobreescritos, mesmo que sem funcionalidades.
     *
     * A implementação dessa forma permite que a escala do canva seja alterada pelo usuário mesmo
     * que a atualização das posições das órbitas não esteja sendo executada.
     */
    private void scaleWatcher() {
        horizontalScale = findViewById(R.id.sb_horizontalscale);
        horizontalScale.setMin(1);
        verticalScale = findViewById(R.id.sb_verticalscale);
        verticalScale.setMin(1);
        rotationVelocity = findViewById(R.id.sb_rotationvelocity);
        rotationVelocity.setMin(0);

        horizontalScale.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                geometry.setScaleX((float) horizontalScale.getProgress() / 50);
                canva.updateImage();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        verticalScale.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                geometry.setScaleY((float) verticalScale.getProgress() / 50);
                canva.updateImage();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

@Override
    protected void onPause() {
        super.onPause();
        spinning = false;
        if(handler != null){
            handler.removeCallbacksAndMessages(null);
        }
    }

//    TRECHO DE CÓDIGO USADO APENAS PARA DEBUG MANTIDO COMO EXEMPLO PARA USOS FUTUROS
//        ArrayList<Double> orbit = geometry.orbitTraceGeometry();
//        String TAG = "Debug geometria Orbit";
//        Log.d(TAG, orbit.toString());
}