package org.simedape.wp.actor;

import akka.actor.AbstractActor;
import org.simedape.wp.domain.message.WarpingPathMessage;

public class ReportWriterActor extends AbstractActor {

    private int count = 0;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(WarpingPathMessage.class, this::onWarpingPathMessage)
                .matchAny(this::unhandled)
                .build();
    }

    private void onWarpingPathMessage(WarpingPathMessage message) {
        count++;
        System.out.println("ReportWriterActor received a message: " + message.clusterIndex() + "-" + message.index());
        System.out.println("Total messages received: " + count);
    }

    @Override
    public void unhandled(Object message) {
        System.out.println("ReportWriterActor received a message");
    }
}
