package br.edu.ucsal.sergiolj.orbitSimulator.geometry;

import androidx.annotation.NonNull;

import br.edu.ucsal.sergiolj.orbitSimulator.util.ColorGenerator;
import br.edu.ucsal.sergiolj.orbitSimulator.util.PolarCoordinates;
import br.edu.ucsal.sergiolj.orbitSimulator.util.SizeGenerator;

import java.util.ArrayList;

public class Geometry {
    private static Geometry instance = null;

    private final int NUMBER_OF_ELEMENTS = 1;
    private final int NUMBER_OF_STRUCTURES = 18;
    private final double MAX_RADIUS = 1000;

    private int elementMinSize = 5;
    private int elementMaxSize = 25;

    private final ArrayList<Element> geometrySet;
    private final ArrayList<Double> orbitRadius;

    private double displacementSum;

    private double scaleX;
    private double scaleY;

    //PRIVATE CONSTRUCTOR SINGLETON
    private Geometry(){
        this.geometrySet = new ArrayList<>();
        this.orbitRadius = new ArrayList<>();
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
     */
    public void populateGeometrySet() {
       this.scaleX = 1.0F;
       this.scaleY = 1.0F;

       double radius;
       double angleTeta;

       this.geometrySet.clear();

       ColorGenerator cg = new ColorGenerator();
       SizeGenerator sg = new SizeGenerator(elementMinSize,elementMaxSize);

        for(int i = 1; i <= NUMBER_OF_STRUCTURES; i++){
            for(int j = 1; j == NUMBER_OF_ELEMENTS; j++){
                Element element = new Element();
                radius = i * MAX_RADIUS / NUMBER_OF_STRUCTURES;
                angleTeta = (j - 1) * 2 * Math.PI / NUMBER_OF_ELEMENTS;

                element.setPosition(new PolarCoordinates(radius, angleTeta));
                element.setColor(cg.genRandColor());
                //Log.d("ELEMENT COLOR", "Element color: "+ element.getColor());
                element.setSize(sg.genSize());
                this.geometrySet.add(element);
            }
        }
    }

    /**
     * CRIAÇÃO DO ELEMENTO PARA O DESENHO DAS ÓRBITAS
     * Cria um array de double com a distância do centro da geometria até cada uma de suas órbitas.
     * @return orbit array from structure.
     */
    public void orbitTraceGeometry() {
        double radius;
        this.orbitRadius.clear();
        for(int i=1; i <= NUMBER_OF_STRUCTURES; i++){
            radius = i * MAX_RADIUS / NUMBER_OF_STRUCTURES;
            orbitRadius.add(radius);
        }
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
                PolarCoordinates oldPos = element.getPosition();
                double newAngle = oldPos.getAngle() + displacementSum/ oldPos.getRadius();
                oldPos.setAngle(newAngle);
            }
    }

    //MÉTODOS DE ATUALIZAÇÃO DE DADOS USADOS PELO LISTENER DA FRAGMENT

    public void updateGeometryColor(int selectColor){
        ColorGenerator cg = new ColorGenerator();
        for(Element element: this.geometrySet){
            element.setColor(cg.genColorTones(selectColor));
        }
    }

    public void updateGeometryElementSize(int min, int max){
        SizeGenerator sg = new SizeGenerator(min, max);
        for(Element element: this.geometrySet){
            element.setSize(sg.genSize());
        }
    }

    public void resetColor(){
        ColorGenerator cg = new ColorGenerator();
        for(Element element: this.geometrySet){
            element.setColor(cg.genRandColor());
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

    public ArrayList<Double> getOrbitRadius() {
        return orbitRadius;
    }

    public double getDisplacementSum() {
    return displacementSum;
    }

    //SETTERS
    public void setScaleX(double scaleX) {
        this.scaleX = scaleX;
    }

    public void setScaleY(double scaleY) {
        this.scaleY = scaleY;
    }

    @NonNull
    @Override
    public String toString() {
        return geometrySet.toString();
    }
    public JSONObject toJson() throws JSONException {
    JSONObject obj = new JSONObject();

    obj.put("scaleX", scaleX);
    obj.put("scaleY", scaleY);
    obj.put("displacementSum", displacementSum);

    JSONArray arr = new JSONArray();

    for (Element el : geometrySet) {
        JSONObject e = new JSONObject();
        e.put("size", el.getSize());
        e.put("color", el.getColor());

        PolarCoordinates pc = el.getPosition();
        JSONObject pos = new JSONObject();
        pos.put("radius", pc.getRadius());
        pos.put("angle", pc.getAngle());

        e.put("position", pos);

        arr.put(e);
    }

    obj.put("elements", arr);

    return obj;
}

public void fromJson(JSONObject obj) throws JSONException {

    this.scaleX = obj.getDouble("scaleX");
    this.scaleY = obj.getDouble("scaleY");
    this.displacementSum = obj.getDouble("displacementSum");

    this.geometrySet.clear();

    JSONArray arr = obj.getJSONArray("elements");

    for (int i = 0; i < arr.length(); i++) {
        JSONObject e = arr.getJSONObject(i);

        Element element = new Element();
        element.setSize(e.getInt("size"));
        element.setColor(e.getInt("color"));

        JSONObject pos = e.getJSONObject("position");
        element.setPosition(new PolarCoordinates(
                pos.getDouble("radius"),
                pos.getDouble("angle")
        ));

        geometrySet.add(element);
    }

    // reconstruir orbitas
    orbitTraceGeometry();
}

}


