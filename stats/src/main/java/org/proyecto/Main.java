package org.proyecto;

import java.util.Arrays;

import static org.proyecto.MedidasExternas.calcularRandIndex;
import static org.proyecto.MedidasInternas.calcularAverageIndex;
import static org.proyecto.MedidasInternas.calcularDunnIndex;


public class Main {

    static GestorCSV gestorCSV = new GestorCSV();

    public static void main(String[] args) throws Exception {
        //leemos csv
        DatosEntrada input = gestorCSV.leerCSV();

        DatosSalida datos = procesarResult(input);
        // procesamos
        System.out.println(datos);

        gestorCSV.guardarCSV(datos);
        //guardamos
    }

    private static DatosSalida procesarResult(DatosEntrada input) {
        double[] dunnIndex = calcularDunnIndex(input);
        double[] averageIndex = calcularAverageIndex(input);
        double averageTotalIndex = Arrays.stream(averageIndex).sum() / averageIndex.length;
        double randIndex = calcularRandIndex(input,input);
        return new DatosSalida(dunnIndex,averageIndex,averageTotalIndex,randIndex); // Veremos que demonios devuelve el patrooon
    }


}