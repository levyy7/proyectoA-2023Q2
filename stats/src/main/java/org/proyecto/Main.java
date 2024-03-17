package org.proyecto;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        System.out.println("4 -> normalizaaaar");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();

        System.out.println("Introduce nombre archivo de los puntitos");
        String puntosPath = scanner.nextLine();
        gestorCSV.normalizadorPrime(puntosPath);
        puntosPath = puntosPath.substring(0, puntosPath.length() - 4);
        puntosPath = puntosPath + "-normalized.csv";
        switch (userInput) {
            case "1": {
                System.out.println("Introduce k");
                String k = scanner.nextLine();
                String clusteringPath = "eneko-" + k + ".csv";
                long elapsedTime = executeEneko(puntosPath, clusteringPath, k);
                DatosEntrada input = gestorCSV.leerCSV(puntosPath, clusteringPath);
                DatosSalida datos = procesarResult(input, input, elapsedTime);
                gestorCSV.guardarCSV("output_stats_clustering.csv", datos);
                gestorJSON.guardarJSON("output_stats_clustering.json", datos);
                break;
            }
            case "2": {
                String clusteringPath = "eneko";

                List<List<DatosSalida>> outputsList = new ArrayList<>();

                for (int k = 0; k < 10; k++) {
                    List<DatosSalida> outputs = new ArrayList<>();
                    for (int i = 2; i <= 10; i++) {
                        String clusteringIterationPath = clusteringPath + "-" + i + ".csv";
                        long elapsedTime = executeEneko(puntosPath, clusteringIterationPath, String.valueOf(i));
                        DatosEntrada input = gestorCSV.leerCSV(puntosPath, clusteringIterationPath);
                        DatosSalida datos = procesarResult(input, input, elapsedTime);
                        outputs.add(datos);

                    }
                    outputsList.add(outputs);
                }
                List<DatosSalida> averageOutput;
                averageOutput = outputsList.getFirst();
                for (int i = 1; i < 10; i++) {
                    for (int j = 0; j < averageOutput.size(); j++) {
                        averageOutput.get(j).sum(outputsList.get(i).get(j));
                    }
                }
                for (DatosSalida datos : averageOutput) {
                    datos.dividir();
                    gestorCSV.guardarCSV(clusteringPath + "_stats_clustering-" + datos.clusters() + ".csv", datos);
                    gestorJSON.guardarJSON(clusteringPath + "_stats_clustering-" + datos.clusters() + ".json", datos);
                }
                for (int i = 0; i < 7; i++) {
                    if (averageOutput.get(i).CHIndex() / averageOutput.get(i + 1).CHIndex() >= 0.95 &&
                            averageOutput.get(i).CHIndex() / averageOutput.get(i + 1).CHIndex() <= 1.05) {
                        System.out.println("elbow is " + averageOutput.get(i).clusters());
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
                DatosSalida datos = procesarResult(input, input2, 0);
                gestorCSV.guardarCSV("output_stats_clustering.csv", datos);
                gestorJSON.guardarJSON("output_stats_clustering.json", datos);
                datos = procesarResult(input2, input, 0);
                gestorCSV.guardarCSV("output_stats_clustering_2.csv", datos);
                gestorJSON.guardarJSON("output_stats_clustering_2.json", datos);
                break;
            }
            case "4": {
                System.out.println("eneko follame ya");
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + userInput);
        }
    }


    private static long executeEneko(String puntosNormalizedPath, String clusteringIterationPath, String k) {
        String rutaEjecutable = "src/main.exe"; // Ruta al ejecutable de C++
        File file = new File(rutaEjecutable);
        String absolute = file.getAbsolutePath();
        try {
            ProcessBuilder pb = new ProcessBuilder(absolute, puntosNormalizedPath, clusteringIterationPath, k);
            pb.directory(file.getParentFile());
            long start = Instant.now().getLong(ChronoField.MILLI_OF_SECOND);
            Process proceso = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) ;

            proceso.waitFor(); // Esperar a que el proceso termine
            long stop = Instant.now().getLong(ChronoField.MILLI_OF_SECOND);
            System.out.println("El ejecutable ha finalizado con código de salida: " + proceso.exitValue());
            return stop - start;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private static DatosSalida procesarResult(DatosEntrada input, DatosEntrada input2, long elapsedTime) {
        double[] dunnIndex = calcularDunnIndex(input);
        double[] averageIndex = calcularAverageIndex(input);
        double averageTotalIndex = Arrays.stream(averageIndex).sum() / averageIndex.length;
        double randIndex = calcularRandIndex(input, input2);
        int clusters = input.clusters();
        double wcss = MedidasInternas.calcularWCSS(input);
        double bcss = MedidasInternas.calcularBCSS(input);
        double CHIndex = MedidasInternas.calcularCalinskiHarabaszIndex(input);
        double DBIndex = MedidasInternas.calcularDaviesBoudinIndex(input);
        double averageSilhouette = MedidasInternas.calcularAverageSilhouette(input);
        return new DatosSalida(dunnIndex, averageIndex, averageTotalIndex, randIndex, wcss, clusters, bcss, CHIndex, DBIndex, averageSilhouette, elapsedTime); // Veremos que demonios devuelve el patrooon
    }
}