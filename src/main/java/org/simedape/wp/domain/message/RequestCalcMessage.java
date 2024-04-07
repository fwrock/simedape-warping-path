package org.simedape.wp.domain.message;

public record RequestCalcMessage(
    int clusterIndex,
    int index,
    double[] simulationPoint,
    double[] timeSeries
) {
}
