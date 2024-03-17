package org.proyecto;

public record DatosSalidaJSON(double[] dunnIndex, double[] averageIndex, double averageTotalIndex, double randIndex,
                              double wcss, int clusters, double bcss, double CHIndex, double DBIndex,
                              double averageSilhouette, long elapsedTime) {
}
