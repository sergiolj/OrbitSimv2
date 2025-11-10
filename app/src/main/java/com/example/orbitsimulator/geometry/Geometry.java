package com.example.orbitsimulator.geometry;

import androidx.annotation.NonNull;

import com.example.orbitsimulator.util.ColorRGB;
import com.example.orbitsimulator.util.PolarCoord;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Supplier;

public class Geometry {
    private static Geometry instance = null;

    private Supplier<ElementTypes> elementFactory;


    private static final int NUMBER_OF_ELEMENTS = 5;
    private static final int NUMBER_OF_STRUCTURES = 12;
    private static final double MAX_RADIUS = 1000;
    private ColorRGB basePalette = new ColorRGB();

    /**
     * Deste modo o arraylist aceita typos de elementos diferentes que implementarem
     * a interface ElementTypes
     */
    private ArrayList<ElementTypes> geometrySet;

    // Deslocamento acumulado dos elementos em suas órbitas
    private double displacementSum;

    private double scaleX;
    private double scaleY;

    private final Random rnd = new Random();

    private Geometry(){
        this.geometrySet = new ArrayList<ElementTypes>();
    }

    public static Geometry getInstance(){
        if(instance==null){
            instance = new Geometry();
        }
        return instance;
    }

    /*
     Para adicionar uma flexibilidade de tipos geométricos foi necessário criar essa função para
     possibilitar a injeção do objeto que será criado.
     */
    public void populateGeometrySet(Supplier<ElementTypes> factory) {
       this.scaleX = 1.0F;
       this.scaleY = 1.0F;
       double dAngle = 0;

       this.elementFactory = factory;

       double radius;
       double angleTeta;

       this.geometrySet.clear();

        for(int i = 1; i <= NUMBER_OF_STRUCTURES; i++){
            for(int j = 1; j <= NUMBER_OF_ELEMENTS; j++){
                ElementTypes element = factory.get();
                radius = i * MAX_RADIUS / NUMBER_OF_STRUCTURES;
                angleTeta = (j - 1) * 2 * Math.PI / NUMBER_OF_ELEMENTS;

                element.setPosition(new PolarCoord(radius,angleTeta));
                element.setColor(basePalette.genColor(basePalette,25));
                this.geometrySet.add(element);

            }
        }
    }

    public ArrayList<Double> orbitTraceGeometry() {
        ArrayList<Double> orbit = new ArrayList<>();
        orbit.clear();
        for(int i=1; i < geometrySet.size()-1; i += NUMBER_OF_ELEMENTS){
            orbit.add(geometrySet.get(i).getPosition().getRadius());
        }
        return orbit;
    }

    public void boost(double dC){
        displacementSum = dC;
    }

    public double getScaleX() {
        return scaleX;
    }

    public void setScaleX(double scaleX) {
        this.scaleX = scaleX;
    }

    public double getScaleY() {
        return scaleY;
    }

    public void setScaleY(double scaleY) {
        this.scaleY = scaleY;
    }

    public ArrayList<ElementTypes> getGeometrySet() {
        return geometrySet;
    }

    @Deprecated
    public ArrayList<ElementTypes> getGeometrySetIncrement(){
        ArrayList<ElementTypes> geometrySetNewPositions = new ArrayList<>();
        if(this.elementFactory != null){
                for(ElementTypes element : this.geometrySet){
                    ElementTypes newElement = this.elementFactory.get();
                    newElement.setColor(element.getColor());
                    newElement.setSize(element.getSize());

                    PolarCoord oldPos = element.getPosition();
                    double newAngle = oldPos.getAngle() + displacementSum/ oldPos.getRadius();

                    newElement.setPosition(new PolarCoord(oldPos.getRadius(), newAngle));

                    geometrySetNewPositions.add(newElement);
                }
        } else{
            throw new RuntimeException("elementFactory não foi inicializado.");
        }
        return geometrySetNewPositions;
    }

    public void updateGeometrySet(){
            for(ElementTypes element : this.geometrySet){
                PolarCoord oldPos = element.getPosition();
                double newAngle = oldPos.getAngle() + displacementSum/ oldPos.getRadius();
                oldPos.setAngle(newAngle);
            }
    }

    public void setElementFactory(Supplier<ElementTypes> elementFactory) {
        this.elementFactory = elementFactory;
    }


    public void setDisplacementSum(double displacementSum) {
        this.displacementSum = displacementSum;
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


