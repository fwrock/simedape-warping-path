package org.simedape.wp.domain.message;

import org.simedape.wp.util.StopWatch;

public record CheckQueueMessage(
    StopWatch stopWatch
) {
}
