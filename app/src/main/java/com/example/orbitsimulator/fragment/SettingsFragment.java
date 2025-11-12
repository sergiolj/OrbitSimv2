package com.example.orbitsimulator.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.orbitsimulator.R;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

public class SettingsFragment extends Fragment {

    private OnColorSelectedListener listener;

    public interface OnColorSelectedListener{
        void onColorSelected(int color);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnColorSelectedListener){
            listener = (OnColorSelectedListener) context;
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
            int color = colorEnvelope.getColor(); // ARGB int
            if (listener != null) {
                listener.onColorSelected(color);
            }
        });
    }
}
