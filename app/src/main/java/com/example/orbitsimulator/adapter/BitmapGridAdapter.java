package com.example.orbitsimulator.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.orbitsimulator.geometry.BitmapRepository;

public class BitmapGridAdapter extends BaseAdapter {

    private Context context;
    private BitmapRepository bitmapRepository;

    public BitmapGridAdapter(BitmapRepository bitmapRepository, Context context) {
        this.bitmapRepository = bitmapRepository;
        this.context = context;
    }

    @Override
    public int getCount() {
        return bitmapRepository.getCount();
    }

    @Override
    public Bitmap getItem(int position) {
        return bitmapRepository.getKeyByIndex(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                // Se não for reciclada, inicialize
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(150, 150)); // Tamanho da miniatura
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            // Defina a imagem
            imageView.setImageBitmap(bitmapRepository.getKeyByIndex(position));
            return imageView;
    }
}
