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
            if (Double.isNaN(dunnDist[i])) dunnDist[i] = 0;
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


    public static double calcularDaviesBoudinIndex(DatosEntrada input) {
        double dbIndex = 0;
        double[] average = new double[input.clusters()];
        int[] numEtiquetas = new int[input.clusters()];
        for (int i = 0; i < input.puntos().size(); ++i) {
            ++numEtiquetas[input.etiquetas().get(i)];
            average[input.etiquetas().get(i)] += calcularDistanciaEuclidiana(input.puntos().get(i), input.centroides().get(input.etiquetas().get(i)));
        }
        for (int j = 0; j < numEtiquetas.length; ++j) {
            average[j] /= numEtiquetas[j];
        }

        double[] nearestCluster = new double[input.clusters()]; // Inicializar a infinito
        int[] nearestIndice = new int[input.clusters()];
        Arrays.fill(nearestCluster, POSITIVE_INFINITY);

        for (int i = 0; i < input.clusters(); ++i)
            for (int j = 0; j < input.clusters(); ++j)
                if (i != j) {
                    nearestCluster[i] = min(nearestCluster[i], calcularDistanciaEuclidiana(input.centroides().get(i), input.centroides().get(j)));
                    nearestIndice[i] = j;
                }

        for (int i = 0; i < input.clusters(); ++i)
            dbIndex += average[i] / nearestCluster[i];

        return dbIndex / input.clusters();
    }

    /*
        Calculating the Silhouette Coefficient: Step-by-Step

        For each data point, calculate two values:

                — Average distance to all other data points within the same cluster (cohesion).

                -> — Average distance to all data points in the nearest neighboring cluster (separation).

                2. Compute the silhouette coefficient for each data point using the formula:

        silhouette coefficient = (separation — cohesion) / max(separation, cohesion)

    3. Calculate the average silhouette coefficient across all data points to obtain the overall silhouette score for the clustering result.*/
    public static double calcularAverageSilhouette(DatosEntrada input) {
        double[] nearestCluster = new double[input.puntos().size()];
        int[] nearestIndice = new int[input.puntos().size()];
        Arrays.fill(nearestCluster, POSITIVE_INFINITY);

        for (int i = 0; i < input.puntos().size(); ++i)
            for (int j = 0; j < input.clusters(); ++j)
                if (input.etiquetas().get(i) != j) {
                    double distanciaEuclidiana = calcularDistanciaEuclidiana(input.puntos().get(i), input.centroides().get(j));
                    if (nearestCluster[i] > distanciaEuclidiana)
                    {
                        nearestCluster[i] = distanciaEuclidiana;
                        nearestIndice[i] = j;
                    }
                }

        double silhouetteAverage = 0;

        for (int i = 0; i < input.puntos().size(); i++) {
            double silhouetteCoefficient = 0;
            double cohesion = 0;
            double separation = 0;


            int countCohesion = 0;
            int countSeparation = 0;
            for (int j = 0; j < input.puntos().size(); j++) {
                if (i != j && input.etiquetas().get(j) == input.etiquetas().get(i)) {
                    countCohesion++;
                    cohesion += calcularDistanciaEuclidiana(input.puntos().get(i), input.puntos().get(j));
                }
                if(input.etiquetas().get(j) == nearestIndice[i]){
                    separation += calcularDistanciaEuclidiana(input.puntos().get(i), input.puntos().get(j));
                    countSeparation++;
                }

            }
            separation /= countSeparation;
            cohesion /= countCohesion;

            silhouetteCoefficient = (separation - cohesion) / max(separation, cohesion);
            silhouetteAverage += silhouetteCoefficient;
        }
        silhouetteAverage = silhouetteAverage / input.puntos().size();
        if (Double.isNaN(silhouetteAverage)) silhouetteAverage = -1;
        return silhouetteAverage;

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

        for (int j = 0; j < input.clusters(); ++j) {
            bcssIndex += w[j] * calcularDistanciaEuclidianaSquared(input.centroides().get(j), puntoMedio);
        }

        return bcssIndex;
    }
}
