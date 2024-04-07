package org.simedape.wp.domain;

import java.util.List;

public record Clustering(
    int numberOfClusters,
    int totalTimeSeries,
    List<SimulationPoint> simulationPoints
) {
}
