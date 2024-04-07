package org.simedape.wp.domain.message;

import org.simedape.wp.metric.dtw.WarpPath;

public record ResponseCalcMessage(
    int index,
    double distance,
    WarpPath warpingPath
) {
}
