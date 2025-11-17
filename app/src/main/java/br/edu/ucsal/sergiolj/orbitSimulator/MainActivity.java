package br.edu.ucsal.sergiolj.orbitSimulator;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.edu.ucsal.sergiolj.orbitSimulator.canvas.GeometryCanvas;
import br.edu.ucsal.sergiolj.orbitSimulator.fragment.SettingsFragment;
import br.edu.ucsal.sergiolj.orbitSimulator.geometry.Geometry;

import br.edu.ucsal.sergiolj.orbitSimulator.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class MainActivity extends AppCompatActivity implements SettingsFragment.OnSettingsSelectedListener {

    private Geometry geometry;
    
    private boolean spinning = false;
    private Handler handler;
    private SeekBar horizontalScale;
    private SeekBar verticalScale;
    private SeekBar rotationVelocity;
    private GeometryCanvas canva;
    private BottomSheetBehavior<View> bottomSheetBehavior;


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

        dataInitializer();
        fragmentInitializer();
        interfaceInitializer();
        
        animationControl();
    }


    private void fragmentInitializer() {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_settings, new SettingsFragment())
                .commit();

        View bottomSheet = findViewById(R.id.fragment_settings);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        bottomSheet.post(()->{
            //Usa uma dimensão diferente para landscape e portrait usando dimens.xml
            bottomSheetBehavior.setPeekHeight(
                    (int) getResources().getDimension(R.dimen.peek_height),
                    true);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            bottomSheetBehavior.setHideable(false);
        });
    }

    /**
     * INICIALIZA AS FUNCIONALIDADES DE CONTROLES DA INTERFACE COM O USUÁRIO
     *
     */
    private void interfaceInitializer() {
        canva = findViewById(R.id.geometryCanvas);

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
        geometry = Geometry.getInstance();
        geometry.orbitTraceGeometry();
        geometry.populateGeometrySet();
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
    public void onSizeChange(int min, int max) {
        geometry.updateGeometryElementSize(min, max);
        canva.updateImage();
    }

    @Override
    public void onColorSelected(int color) {
        geometry.updateGeometryColor(color);
        canva.updateImage();
    }

    @Override
    public void onResetColor() {
        geometry.resetColor();
        canva.updateImage();
    }

    @Override
    protected void onPause() {
        GeometryStorage.saveGeometry(
            this,
            geometry.getScaleX(),
            geometry.getScaleY(),
            0, // ou geometry.getDisplacementSum() se quiser salvar a velocidade
            new ColorRGB(50,100,200) // ou geometry.getBasePalette()
        );

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

    //        bottomSheet.post(() -> {
//            int peekHeight = (int) (getResources().getDisplayMetrics().density * 150); // 10 + margens
//            bottomSheetBehavior.setPeekHeight(peekHeight, false);
//        });
}