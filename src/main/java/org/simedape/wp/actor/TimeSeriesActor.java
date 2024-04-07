package org.simedape.wp.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import org.simedape.wp.domain.message.ResponseCalcMessage;
import org.simedape.wp.exception.InvalidMessageException;
import org.simedape.wp.domain.message.RequestCalcMessage;
import org.simedape.wp.metric.dtw.FastDTW;
import org.simedape.wp.metric.timeseries.TimeSeries;
import org.simedape.wp.metric.util.DistanceFunctionFactory;

public class TimeSeriesActor extends AbstractActor {

    private final ActorRef reportWriterActor;

    public TimeSeriesActor(ActorRef reportWriterActor) {
        this.reportWriterActor = reportWriterActor;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(RequestCalcMessage.class, this::onRequestCalcMessage)
                .matchAny(this::unhandled)
                .build();
    }

    private void onRequestCalcMessage(RequestCalcMessage message) {

        final var ts1 = new TimeSeries(message.simulationPoint());
        final var ts2 = new TimeSeries(message.timeSeries());

        final var info = FastDTW.getWarpInfoBetween(ts1, ts2,1, DistanceFunctionFactory.EUCLIDEAN_DIST_FN);

        getSender().tell(new ResponseCalcMessage(message.index(),
                        info.getDistance(),
                        info.getPath()),
                ActorRef.noSender());

        getContext().stop(getSelf());
    }


    @Override
    public void unhandled(Object message) {
        throw new InvalidMessageException("Invalid message received by TimeSeriesActor.");
    }
}
