package org.proyecto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.Double.max;
import static java.lang.Double.min;
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
            case "1": {
                System.out.println("Introduce nombre archivo de clustering");
                String clusteringPath = scanner.nextLine();
                DatosEntrada input = gestorCSV.leerCSV(puntosPath, clusteringPath);
                input = normalizadorPrime(input);
                DatosSalida datos = procesarResult(input, input);
                gestorCSV.guardarCSV("output_stats_clustering.csv", datos);
                gestorJSON.guardarJSON("output_stats_clustering.json", datos);
                break;
            }
            case "2": {
                System.out.println("Introduce nombre archivo de clustering");
                String clusteringPath = scanner.nextLine();
                clusteringPath = clusteringPath.substring(0, clusteringPath.length() - 4);
                List<DatosSalida> outputs = new ArrayList<>();
                for (int i = 2; i <= 10; i++) {
                    DatosEntrada input = gestorCSV.leerCSV(puntosPath, clusteringPath + "-" + i + ".csv");
                    DatosSalida datos = procesarResult(input, input);
                    outputs.add(datos);
                    gestorCSV.guardarCSV(clusteringPath + "_stats_clustering-" + datos.clusters() + ".csv", datos);
                    gestorJSON.guardarJSON(clusteringPath + "_stats_clustering-" + datos.clusters() + ".json", datos);
                }
                for (int i = 0; i < 7; i++) {
                    if (outputs.get(i).CHIndex() / outputs.get(i + 1).CHIndex() >= 0.95 &&
                            outputs.get(i).CHIndex() / outputs.get(i + 1).CHIndex() <= 1.05) {
                        System.out.println("elbow is " + outputs.get(i).clusters());
                        break;
                    }
                }

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
        int clusters = input.clusters();
        double wcss = MedidasInternas.calcularWCSS(input);
        double bcss = MedidasInternas.calcularBCSS(input);
        double CHIndex = MedidasInternas.calcularCalinskiHarabaszIndex(input);
        return new DatosSalida(dunnIndex, averageIndex, averageTotalIndex, randIndex, wcss, clusters, bcss, CHIndex); // Veremos que demonios devuelve el patrooon
    }


    private static DatosEntrada normalizadorPrime(DatosEntrada input) {
        double[] minimo = new double[input.dimensiones()];
        double[] maximo = new double[input.dimensiones()];
        for (Punto p : input.puntos()) {
            for (int j = 0; j < input.dimensiones(); ++j) {
                minimo[j] = min(p.valores()[j], minimo[j]);
                maximo[j] = max(p.valores()[j], maximo[j]);
            }
        }

        for (Punto p : input.puntos()) {
            for (int j = 0; j < input.dimensiones(); ++j) {
                p.valores()[j] = (p.valores()[j] - minimo[j]) / (maximo[j] - minimo[j]);
            }
        }
        return input;
    }
}