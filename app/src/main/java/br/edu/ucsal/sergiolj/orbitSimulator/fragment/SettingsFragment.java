package br.edu.ucsal.sergiolj.orbitSimulator.fragment;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import br.edu.ucsal.sergiolj.orbitSimulator.R;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

public class SettingsFragment extends Fragment {

    private OnSettingsSelectedListener listener;

    public interface OnSettingsSelectedListener {
        void onSizeChange(int min, int max);
        void onColorSelected(int color);

        void onResetColor();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnSettingsSelectedListener){
            listener = (OnSettingsSelectedListener) context;
        }else{
            throw new RuntimeException(context.toString() +
                    "Must implement OnColorSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ColorPickerView colorPickerView = view.findViewById(R.id.colorPickerView);

        colorPickerView.setColorListener((ColorEnvelopeListener) (colorEnvelope, fromUser) -> {
            if(!fromUser) return; // só reage a interações reais, sem isso ele reage após a inicialização

            int color = colorEnvelope.getColor(); // ARGB int
            if (listener != null) {
                listener.onColorSelected(color);
                Log.d("COLOR_PICKER", "Cor recebida do picker: " + color);
            }
        });

        SeekBar sbMinSize = view.findViewById(R.id.sb_min_size);
        SeekBar sbMaxSize = view.findViewById(R.id.sb_max_size);
        Button btSizeChange = view.findViewById(R.id.btn_aply_settings);

        btSizeChange.setOnClickListener(v -> {
            if(listener!=null){
                listener.onSizeChange(sbMinSize.getProgress(), sbMaxSize.getProgress());
            }
        });

        Button btnResetColor = view.findViewById(R.id.btn_reset);
        btnResetColor.setOnClickListener(v -> {
            if(listener!=null){
                listener.onResetColor();
            }
        });

    }
}
