package org.simedape.wp.domain;

import java.util.List;

public record DynamicTimeWarpingResult(
    double distance,
    List<int[]> path
) {
}
