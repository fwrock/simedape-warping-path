package org.simedape.wp.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorSystem;
import main.java.org.simedape.wp.domain.message.ClusterDoneMessage;
import org.simedape.wp.domain.message.CheckQueueMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckSystemActor extends AbstractActor {

    private final Logger logger = LoggerFactory.getLogger(CheckSystemActor.class);

    private int clusterDoneCount = 0;

    private int totalClusters;

    public CheckSystemActor(int totalClusters) {
        this.totalClusters = totalClusters;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ClusterDoneMessage.class, message -> {
                    clusterDoneCount++;
                    logger.info("CheckSystemActor received a message from cluster={}", message.clusterIndex());
                    if (clusterDoneCount >= totalClusters) {
                        ActorSystem system = context().system();
                        system.terminate();
                    }
                    logger.info("Total messages received={}", clusterDoneCount);
                })
                .match(CheckQueueMessage.class, message -> {
                    logger.info("Total messages received={}", clusterDoneCount);
                    if (clusterDoneCount >= totalClusters) {
                        ActorSystem system = context().system();
                        system.terminate();
                    }
                })
                .matchAny(o -> logger.warn("CheckSystemActor received a message"))
                .build();
    }
}
