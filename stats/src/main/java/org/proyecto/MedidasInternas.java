package org.proyecto;

import java.util.Arrays;

import static java.lang.Double.*;
import static java.lang.Math.pow;

public class MedidasInternas {
    public static double[] calcularAverageIndex(DatosEntrada input) {
        double[] distAverage = new double[input.clusters()];
        int[] numPuntos = new int[input.clusters()];
        Arrays.fill(distAverage, 0);
        Arrays.fill(numPuntos, 0);

        for (int i = 0; i < input.puntos().size(); i++) {
            Integer etiquetaIndex = input.etiquetas().get(i);
            Punto p = input.puntos().get(i);
            Punto centroide = input.centroides().get(etiquetaIndex);
            double dist = calcularDistanciaEuclidiana(p, centroide);
            distAverage[etiquetaIndex] += dist;
            numPuntos[etiquetaIndex]++;
        }
        for (int i = 0; i < input.clusters(); i++) {
            distAverage[i] /= numPuntos[i];
        }

        return distAverage;
    }

    public static double[] calcularDunnIndex(DatosEntrada input) {
        double[] distMin = new double[input.clusters()];
        double[] distMax = new double[input.clusters()];
        Arrays.fill(distMax, NEGATIVE_INFINITY);
        Arrays.fill(distMin, POSITIVE_INFINITY);

        for (int i = 0; i < input.puntos().size(); i++) {
            Integer etiquetaIndex = input.etiquetas().get(i);
            Punto p = input.puntos().get(i);
            Punto centroide = input.centroides().get(etiquetaIndex);
            double dist = calcularDistanciaEuclidiana(p, centroide);
            distMin[etiquetaIndex] = min(dist, distMin[etiquetaIndex]);
            distMax[etiquetaIndex] = max(dist, distMax[etiquetaIndex]);
        }
        double[] dunnDist = new double[input.clusters()];
        for (int i = 0; i < dunnDist.length; i++) {
            dunnDist[i] = distMin[i] / distMax[i];
        }
        return dunnDist;

    }

    public static double calcularDistanciaEuclidiana(Punto p, Punto centroide) {
        double sum = 0;
        for (int i = 0; i < p.valores().length; i++) {
            sum += pow(p.valores()[i] - centroide.valores()[i], 2);
        }
        return Math.sqrt(sum);
    }

    public static double calcularDistanciaEuclidianaSquared(Punto p, Punto centroide) {
        double sum = 0;
        for (int i = 0; i < p.valores().length; i++) {
            sum += pow(p.valores()[i] - centroide.valores()[i], 2);
        }
        return sum;
    }

    public static double calcularCalinskiHarabaszIndex(DatosEntrada input) {
        double bcss = calcularBCSS(input);
        double wcss = calcularWCSS(input);
        return ((bcss * (input.puntos().size() - input.clusters())) / (wcss * (input.clusters() - 1)));
    }

    public static double calcularWCSS(DatosEntrada input) {
        double sum = 0;
        for (int i = 0; i < input.puntos().size(); ++i) {
            sum += calcularDistanciaEuclidianaSquared(input.puntos().get(i), input.centroides().get(input.etiquetas().get(i)));
        }
        return sum;
    }

    public static double calcularBCSS(DatosEntrada input) {
        Punto puntoMedio = new Punto(new double[input.dimensiones()]);

        for (Punto p : input.puntos()) { //Calculadora de punto medio padre master
            for (int i = 0; i < input.dimensiones(); ++i) {
                puntoMedio.valores()[i] += p.valores()[i];
            }
        }
        for (int i = 0; i < input.dimensiones(); i++) {
            puntoMedio.valores()[i] /= input.puntos().size();
        }

        double bcssIndex = 0; // Necesitamos que los puntos esten weighted

        int[] w = new int[input.clusters()];
        for (int t : input.etiquetas()) {
            ++w[t];
        }

        for (int j = 0; j < input.dimensiones(); ++j) {
            bcssIndex += w[j] * calcularDistanciaEuclidianaSquared(input.centroides().get(j), puntoMedio);
        }

        return bcssIndex;
    }
}
