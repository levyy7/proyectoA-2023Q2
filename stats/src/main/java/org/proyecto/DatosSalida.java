package org.proyecto;

import java.math.BigInteger;
import java.util.Objects;

public final class DatosSalida {
    private BigInteger elapsedTime;
    private double[] dunnIndex;
    private double[] averageIndex;
    private double averageTotalIndex;
    private double randIndex;
    private double wcss;
    private int clusters;
    private double bcss;
    private double CHIndex;
    private double DBIndex;
    private double averageSilhouette;

    public DatosSalida(double[] dunnIndex, double[] averageIndex, double averageTotalIndex, double randIndex,
                       double wcss, int clusters, double bcss, double CHIndex, double DBIndex,
                       double averageSilhouette, BigInteger elapsedTime) {
        this.dunnIndex = dunnIndex;
        this.averageIndex = averageIndex;
        this.averageTotalIndex = averageTotalIndex;
        this.randIndex = randIndex;
        this.wcss = wcss;
        this.clusters = clusters;
        this.bcss = bcss;
        this.CHIndex = CHIndex;
        this.DBIndex = DBIndex;
        this.averageSilhouette = averageSilhouette;
        this.elapsedTime = elapsedTime;
    }

    void sum(DatosSalida datos) {
        for (int i = 0; i < dunnIndex.length; i++) {
            this.dunnIndex[i] += datos.dunnIndex[i];
        }
        for (int i = 0; i < averageIndex.length; i++) {
            this.averageIndex[i] += datos.averageIndex[i];
        }
        this.averageTotalIndex += datos.averageTotalIndex;
        this.randIndex += datos.randIndex;
        this.wcss += datos.wcss;
        this.clusters += datos.clusters;
        this.bcss += datos.bcss;
        this.CHIndex +=  datos.CHIndex;
        this.DBIndex += datos.DBIndex;
        this.averageSilhouette += datos.averageSilhouette;
        this.elapsedTime = elapsedTime.add(datos.elapsedTime);

    }

    void dividir(int iterations) {
        for (int i = 0; i < dunnIndex.length; i++) {
            this.dunnIndex[i] /= iterations;
        }
        for (int i = 0; i < averageIndex.length; i++) {
            this.averageIndex[i] /= iterations;
        }
        this.averageTotalIndex /= iterations;
        this.randIndex /= iterations;
        this.wcss /= iterations;
        this.clusters /= iterations;
        this.bcss /= iterations;
        this.CHIndex /= iterations;
        this.DBIndex /= iterations;
        this.averageSilhouette /= iterations;
        this.elapsedTime = elapsedTime.divide(new BigInteger(String.valueOf(iterations)));

    }

    public double[] dunnIndex() {
        return dunnIndex;
    }

    public double[] averageIndex() {
        return averageIndex;
    }

    public double averageTotalIndex() {
        return averageTotalIndex;
    }

    public double randIndex() {
        return randIndex;
    }

    public double wcss() {
        return wcss;
    }

    public int clusters() {
        return clusters;
    }

    public double bcss() {
        return bcss;
    }

    public double CHIndex() {
        return CHIndex;
    }

    public double DBIndex() {
        return DBIndex;
    }

    public double averageSilhouette() {
        return averageSilhouette;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (DatosSalida) obj;
        return Objects.equals(this.dunnIndex, that.dunnIndex) &&
                Objects.equals(this.averageIndex, that.averageIndex) &&
                Double.doubleToLongBits(this.averageTotalIndex) == Double.doubleToLongBits(that.averageTotalIndex) &&
                Double.doubleToLongBits(this.randIndex) == Double.doubleToLongBits(that.randIndex) &&
                Double.doubleToLongBits(this.wcss) == Double.doubleToLongBits(that.wcss) &&
                this.clusters == that.clusters &&
                Double.doubleToLongBits(this.bcss) == Double.doubleToLongBits(that.bcss) &&
                Double.doubleToLongBits(this.CHIndex) == Double.doubleToLongBits(that.CHIndex) &&
                Double.doubleToLongBits(this.DBIndex) == Double.doubleToLongBits(that.DBIndex) &&
                Double.doubleToLongBits(this.averageSilhouette) == Double.doubleToLongBits(that.averageSilhouette);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dunnIndex, averageIndex, averageTotalIndex, randIndex, wcss, clusters, bcss, CHIndex, DBIndex, averageSilhouette);
    }

    @Override
    public String toString() {
        return "DatosSalida[" +
                "dunnIndex=" + dunnIndex + ", " +
                "averageIndex=" + averageIndex + ", " +
                "averageTotalIndex=" + averageTotalIndex + ", " +
                "randIndex=" + randIndex + ", " +
                "wcss=" + wcss + ", " +
                "clusters=" + clusters + ", " +
                "bcss=" + bcss + ", " +
                "CHIndex=" + CHIndex + ", " +
                "DBIndex=" + DBIndex + ", " +
                "averageSilhouette=" + averageSilhouette + ']';
    }

    public BigInteger elapsedTime() {
        return elapsedTime;
    }
}
