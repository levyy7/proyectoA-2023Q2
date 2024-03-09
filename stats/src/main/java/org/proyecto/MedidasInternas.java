package org.proyecto;

import java.util.Arrays;

import static java.lang.Double.*;
import static java.lang.Double.max;
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
}
