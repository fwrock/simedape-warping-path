package org.simedape.wp.util;

import org.simedape.wp.domain.Clustering;
import org.simedape.wp.domain.SimulationPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generators {

    public static double[] generateRandomDoubleArray(int index, int size) {
        double[] array = new double[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextDouble();
        }
        array[0] = index;
        return array;
    }

    public static double[] generateRandomDoubleArray(int size) {
        double[] array = new double[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextDouble();
        }
        return array;
    }

    public static double[][] generateTimeSeriesDataset(int size, int length) {
        double[][] dataset = new double[size][length];
        for (int i = 0; i < size; i++) {
            dataset[i] = generateRandomDoubleArray(i, length);
        }
        return dataset;
    }

    public static String generateClusteringStringJson(int size, int timeSeriesSize) {
        StringBuilder clustering = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            clustering.append("{\"index\":").append(i).append(",\"simulationPoint\":[");
            clustering.append(String.join(",", String.valueOf(generateRandomDoubleArray(timeSeriesSize))));
            clustering.append("],\"filePath\":\"file").append(i).append("\",\"numberOfClusters\":").append(size);
            clustering.append(",\"totalTimeSeries\":").append(size).append("}");
            if (i < size - 1) {
                clustering.append(",");
            }
        }
        clustering.append("]");
        return clustering.toString();
    }

    public static Clustering generateClusteringStringObject(int size, int timeSeriesSize) {
        List<SimulationPoint> simulationPoints = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            simulationPoints.add(new SimulationPoint(i + 1, generateRandomDoubleArray(timeSeriesSize)));
        }
        return new Clustering(size, timeSeriesSize, simulationPoints);
    }

    public static void main(String[] args) {
        System.out.println(String.join(",", String.valueOf(generateRandomDoubleArray(10))));
        double[][] timeSeriesDataset = generateTimeSeriesDataset(10, 10);
        for (double[] series : timeSeriesDataset) {
            System.out.println(String.join(",", String.valueOf(series)));
        }
        System.out.println(generateClusteringStringJson(10, 10));
    }
}
