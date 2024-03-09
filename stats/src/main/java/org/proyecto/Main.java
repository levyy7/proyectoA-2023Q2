package org.proyecto;

import java.util.Arrays;
import java.util.Scanner;

import static org.proyecto.MedidasExternas.calcularRandIndex;
import static org.proyecto.MedidasInternas.calcularAverageIndex;
import static org.proyecto.MedidasInternas.calcularDunnIndex;


public class Main {

    private static final GestorCSV gestorCSV = new GestorCSV();
    private static final GestorJSON gestorJSON = new GestorJSON();

    public static void main(String[] args) throws Exception {
        System.out.println("1 -> Obtener stats de un clustering");
        System.out.println("2 -> Obtener stats de un clustering con elbow");
        System.out.println("3 -> Comparar 2 stats de clustering");
        System.out.println("4 -> que???");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        System.out.println("Introduce nombre archivo de los puntitos");
        String puntosPath = scanner.nextLine();
        switch (userInput) {
            case "1", "2": { // TODO add elbow
                System.out.println("Introduce nombre archivo de clustering");
                String clusteringPath = scanner.nextLine();
                DatosEntrada input = gestorCSV.leerCSV(puntosPath, clusteringPath);
                DatosSalida datos = procesarResult(input, input);
                gestorCSV.guardarCSV("output_stats_clustering.csv", datos);
                gestorJSON.guardarJSON("output_stats_clustering.json", datos);
                break;
            }
            case "3": {
                System.out.println("Introduce nombre archivo de clustering 1");
                String clusteringPath = scanner.nextLine();
                System.out.println("Introduce nombre archivo de clustering 2");
                String clusteringPath2 = scanner.nextLine();
                DatosEntrada input = gestorCSV.leerCSV(puntosPath, clusteringPath);
                DatosEntrada input2 = gestorCSV.leerCSV(puntosPath, clusteringPath2);
                DatosSalida datos = procesarResult(input, input2);
                gestorCSV.guardarCSV("output_stats_clustering.csv", datos);
                gestorJSON.guardarJSON("output_stats_clustering.json", datos);
                datos = procesarResult(input2, input);
                gestorCSV.guardarCSV("output_stats_clustering_2.csv", datos);
                gestorJSON.guardarJSON("output_stats_clustering_2.json", datos);
                break;
            }
            case "4": {
                System.out.println("quiero morir");
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + userInput);
        }
    }

    private static DatosSalida procesarResult(DatosEntrada input, DatosEntrada input2) {
        double[] dunnIndex = calcularDunnIndex(input);
        double[] averageIndex = calcularAverageIndex(input);
        double averageTotalIndex = Arrays.stream(averageIndex).sum() / averageIndex.length;
        double randIndex = calcularRandIndex(input, input2);
        return new DatosSalida(dunnIndex, averageIndex, averageTotalIndex, randIndex); // Veremos que demonios devuelve el patrooon
    }


}