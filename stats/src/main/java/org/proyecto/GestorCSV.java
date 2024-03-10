package org.proyecto;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Double.max;
import static java.lang.Double.min;

public class GestorCSV {
    public DatosEntrada leerCSV(String puntosPath, String clusteringPath) throws Exception {
        File file = new File("data/output/kmeans/" + clusteringPath);
        String absolutePath = file.getAbsolutePath();
        File file2 = new File("data/input/" + puntosPath);
        String absolutePath2 = file2.getAbsolutePath();
        List<String[]> enekoDatos = readData(Path.of(absolutePath));
        List<String[]> profeDatos = readData(Path.of(absolutePath2));

        int dimensiones = Integer.parseInt(enekoDatos.getFirst()[0]);
        int clusters = Integer.parseInt(enekoDatos.getFirst()[1]);
        List<Punto> puntos = profeDatos.stream().map(elem -> new Punto(Arrays.stream(elem).mapToDouble(Double::parseDouble).toArray())).toList();
        List<Punto> centroides = new ArrayList<>();
        for (int i = 1; i <= clusters; i++) {
            centroides.add(new Punto(Arrays.stream(enekoDatos.get(i)).mapToDouble(Double::parseDouble).toArray()));
        }
        List<Integer> etiquetas = new ArrayList<>();
        for (int i = clusters + 1; i <= clusters + puntos.size(); i++) {
            etiquetas.add(Integer.valueOf(enekoDatos.get(i)[0]));
        }

        return new DatosEntrada(dimensiones, clusters, puntos, centroides, etiquetas);

    }

    public void guardarCSV(String path, DatosSalida datos) throws IOException {
        List<String[]> args = new ArrayList<>();
        String[] dunnIndexArray = new String[datos.dunnIndex().length];
        for (int i = 0; i < datos.dunnIndex().length; i++) dunnIndexArray[i] = String.valueOf(datos.dunnIndex()[i]);
        args.add(dunnIndexArray);
        String[] averageIndexArray = new String[datos.averageIndex().length];
        for (int i = 0; i < datos.averageIndex().length; i++)
            averageIndexArray[i] = String.valueOf(datos.averageIndex()[i]);
        args.add(averageIndexArray);
        args.add(new String[]{String.valueOf(datos.averageTotalIndex())});
        args.add(new String[]{String.valueOf(datos.randIndex())});
        writeData(Path.of("data/output/stats/" + path), args);

    }

    private static void normalizadorPrime(String path) {
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

    public List<String[]> readData(Path filePath) throws Exception {
        try (Reader reader = Files.newBufferedReader(filePath)) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                return csvReader.readAll();
            }
        }
    }

    public static void writeData(Path filePath, List<String[]> args) throws IOException {
        File file = new File(String.valueOf(filePath));
        FileWriter outputfile = new FileWriter(file);

        CSVWriter writer = new CSVWriter(outputfile);

        writer.writeAll(args, false);

        writer.close();
    }
}
