package org.simedape.wp.domain.message;

import org.simedape.wp.metric.dtw.WarpPath;

public record WarpingPathMessage(
    int clusterIndex,
    int index,
    double distance,
    WarpPath warpingPath
) {
}
