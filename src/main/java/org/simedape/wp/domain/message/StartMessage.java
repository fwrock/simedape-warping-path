package org.simedape.wp.domain.message;

public record StartMessage(
    int index,
    double[] simulationPoint,
    String datasetPath
) {
}
