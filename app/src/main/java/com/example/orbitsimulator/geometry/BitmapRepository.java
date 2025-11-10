package com.example.orbitsimulator.geometry;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.orbitsimulator.R;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Repositório Singleton para armazenamento dos bitmaps
 */
public class BitmapRepository {

    private static BitmapRepository instance = null;

    // Uso do LinkedHashMap para manter a ordem dos elementos e a consequente imagem desses no app.
    private Map<String, Bitmap> bitmapMapSet = new LinkedHashMap<>();
    
    private BitmapRepository(Context context){
        loadBitmaps(context);
    }

    public static synchronized BitmapRepository getInstance(Context context){
        if(instance == null){
            instance = new BitmapRepository(context.getApplicationContext());
        }
        return instance;
    }

    //Método utilizado para adicionar os bitmaps no LinkedHashMap inidicando uma chave e acionando
    //um método específico usando BitmapFatory
    private void loadBitmaps(Context context) {
        bitmapMapSet.put("planet_blue", loadBitmap(context, R.drawable.planet_blue));
        bitmapMapSet.put("planet_green", loadBitmap(context, R.drawable.planet_green));
        bitmapMapSet.put("planet_red", loadBitmap(context, R.drawable.planet_red));
        bitmapMapSet.put("planet_orange", loadBitmap(context, R.drawable.planet_orange));
    }

    private Bitmap loadBitmap(Context context, int element) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = true;
        return BitmapFactory.decodeResource(context.getResources(), element, options);
    }

    public Bitmap getKeyByIndex(int index){
        return (Bitmap) bitmapMapSet.keySet().toArray()[index];
    }

    public Bitmap getBitmapByKey(String key){
        return bitmapMapSet.get(key);
    }

    public int getCount(){
        return bitmapMapSet.size();
    }

    //Metodo pode ser usado para apagar os dados do repositório em caso de falta de memória
    //para o app. Ao fazer isso, caso o usuário deseje escolher outro bitmap todos os arquivos serão
    //novamente carregados.
    public void clear(){
        for(Bitmap bitmap : bitmapMapSet.values()){
            bitmap.recycle();
        }
        bitmapMapSet.clear();
        instance = null;
    }

}
