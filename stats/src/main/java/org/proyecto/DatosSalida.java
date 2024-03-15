package org.proyecto;

public record DatosSalida(double[] dunnIndex, double[] averageIndex, double averageTotalIndex, double randIndex,
                          double wcss, int clusters, double bcss, double CHIndex, double DBIndex,
                          double averageSilhouette) {
}
