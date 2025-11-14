package com.example.orbitsimulator.geometry;

import androidx.annotation.NonNull;

import com.example.orbitsimulator.util.ColorRGB;
import com.example.orbitsimulator.util.PolarCoord;

import java.util.ArrayList;
import java.util.function.Supplier;

public class Geometry {
    private static Geometry instance = null;

    private static final int NUMBER_OF_ELEMENTS = 1;
    private static final int NUMBER_OF_STRUCTURES = 10;
    private static final double MAX_RADIUS = 1000;
    private ColorRGB basePalette = new ColorRGB();

    private final ArrayList<Element> geometrySet;

    private double displacementSum;

    private double scaleX;
    private double scaleY;

    //PRIVATE CONSTRUCTOR SINGLETON
    private Geometry(){
        this.geometrySet = new ArrayList<>();
    }

    //GETTER SINGLETON
    public static Geometry getInstance(){
        if(instance==null){
            instance = new Geometry();
        }
        return instance;
    }

    /**
     * CRIAÇÃO DO ELEMENTO PARA O DESENHO DOS OBJETOS NAS ÓRBITAS
     * Para adicionar uma flexibilidade de tipos geométricos foi necessário criar essa função para
     * possibilitar a injeção do objeto que será criado.
     */
    public void populateGeometrySet() {
       this.scaleX = 1.0F;
       this.scaleY = 1.0F;

       double radius;
       double angleTeta;

       this.geometrySet.clear();

        for(int i = 1; i <= NUMBER_OF_STRUCTURES; i++){
            for(int j = 1; j <= NUMBER_OF_ELEMENTS; j++){
                Element element = new Element();
                radius = i * MAX_RADIUS / NUMBER_OF_STRUCTURES;
                angleTeta = (j - 1) * 2 * Math.PI / NUMBER_OF_ELEMENTS;

                element.setPosition(new PolarCoord(radius,angleTeta));
                element.setColor(basePalette.genColor(basePalette,basePalette.getVariance()));
                this.geometrySet.add(element);

            }
        }
    }

    /**
     * CRIAÇÃO DO ELEMENTO PARA O DESENHO DAS ÓRBITAS
     * Cria um array de double com a distância do centro da geometria até cada uma de suas órbitas.
     * @return orbit array from structure.
     */
    public ArrayList<Double> orbitTraceGeometry() {
        ArrayList<Double> orbit = new ArrayList<>();
        int size = NUMBER_OF_ELEMENTS * NUMBER_OF_STRUCTURES;
        double radius;
        orbit.clear();
        for(int i=1; i <= size; i += NUMBER_OF_ELEMENTS){
            radius = i * MAX_RADIUS / NUMBER_OF_STRUCTURES;
            orbit.add(radius);
        }
        return orbit;
    }

    /**
     * DEFINIÇÃO DA DIFERENÇA RELATIVA DE POSIÇÃO DO ELEMENTO
     * Boost deixou de ser incremental para que seja possível diminuir a velocidade com
     * a Seekbar e com isso seu nome mudou para move()
     * @param dC
     */
    public void move(double dC){
        displacementSum = dC;
    }

    /**
     * CRIAÇÃO DOS DADOS DA ANIMAÇÃO
     * Ao invés de criar uma nova estrutura de Array para a execução dos movimentos das órbitas
     * apenas foram atualizadas as posições dos elementos do array principal geometrySet.
     */
    public void updateGeometrySet(){
            for(Element element : this.geometrySet){
                PolarCoord oldPos = element.getPosition();
                double newAngle = oldPos.getAngle() + displacementSum/ oldPos.getRadius();
                oldPos.setAngle(newAngle);
            }
    }

    public void updateGeometryColor(){
        for(Element element: this.geometrySet){
            element.setColor(basePalette.genColor(basePalette,25));
        }
    }

    public void updateGeometrySize(int min, int max){
        for(Element element: this.geometrySet){
            element.setMinSize(min);
            element.setMaxSize(max);
            element.setSize(element.genSize());
        }
    }

    //GETTERS
    public ArrayList<Element> getGeometrySet() {
        return geometrySet;
    }

    public double getScaleX() {
        return scaleX;
    }
    public double getScaleY() {
        return scaleY;
    }

    //SETTERS
    public void setScaleX(double scaleX) {
        this.scaleX = scaleX;
    }

    public void setScaleY(double scaleY) {
        this.scaleY = scaleY;
    }

    public void setBasePalette(ColorRGB basePalette) {
        this.basePalette = basePalette;
    }

    @NonNull
    @Override
    public String toString() {
        return geometrySet.toString();
    }

}


