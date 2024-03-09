package org.proyecto;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GestorCSV {
    public DatosEntrada leerCSV() throws Exception {
        File file = new File("data/eneko.csv");
        String absolutePath = file.getAbsolutePath();
        File file2 = new File("data/profe.csv");
        String absolutePath2 = file2.getAbsolutePath();
        List<String[]> enekoDatos = readAllLines(Path.of(absolutePath));
        List<String[]> profeDatos = readAllLines(Path.of(absolutePath2));

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

    public void guardarCSV(DatosSalida datos) {

    }

    public List<String[]> readAllLines(Path filePath) throws Exception {
        try (Reader reader = Files.newBufferedReader(filePath)) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                return csvReader.readAll();
            }
        }
    }
}
