package org.simedape.wp.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import main.java.org.simedape.wp.domain.message.ClusterDoneMessage;
import org.simedape.wp.domain.message.RequestCalcMessage;
import org.simedape.wp.domain.message.StartMessage;
import org.simedape.wp.exception.InvalidMessageException;
import org.simedape.wp.util.Generators;
import org.simedape.wp.domain.message.ResponseCalcMessage;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

public class ClusterActor extends AbstractActor {

    private final Logger logger = LoggerFactory.getLogger(ClusterActor.class);

    private final ActorRef checkSystemActor;

    private int clusterIndex;

    private int count = 0;
    private int total = 0;

    private int timeSeriesDataPoints;
    private int numberOfTimeSeriesByCluster;

    public ClusterActor(ActorRef checkSystemActor, Integer timeSeriesDataPoints, Integer numberOfTimeSeriesByCluster) {
        this.checkSystemActor = checkSystemActor;
        this.timeSeriesDataPoints = timeSeriesDataPoints;
        this.numberOfTimeSeriesByCluster = numberOfTimeSeriesByCluster;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(StartMessage.class, this::onStartMessage)
                .match(ResponseCalcMessage.class, this::onResponseCalcMessage)
                .matchAny(this::unhandled)
                .build();
    }

    private void onResponseCalcMessage(ResponseCalcMessage message) {
        count++;
        if (count == total) {
            logger.info("All messages received={}", clusterIndex);
            checkSystemActor.tell(new ClusterDoneMessage(clusterIndex), ActorRef.noSender());
        }
    }

    private void onStartMessage(StartMessage message) {
        clusterIndex = message.index();

        logger.info("ClusterActor {} received a message", message.index());

        double[][] dataset = Generators.generateTimeSeriesDataset(numberOfTimeSeriesByCluster, timeSeriesDataPoints);

        total = dataset.length;

        logger.info("Processing time series...");
        for (double[] row : dataset) {
            processTimeSeries(message.index(), message.simulationPoint(), row);
        }
        logger.info("Time series processed.");
    }

    private void processTimeSeries(int clusterIndex, double[] simulationPoint, double[] timeSeries) {

        final var timeSeriesActor = getContext().actorOf(Props.create(TimeSeriesActor.class, checkSystemActor),
                "TimeSeriesActor-" + (int) timeSeries[0]);

        timeSeriesActor.tell(new RequestCalcMessage(clusterIndex, (int) timeSeries[0],
                        simulationPoint,
                        sliceArray(timeSeries, 1, timeSeries.length)),
                getSelf());

    }

    private double[] sliceArray(double[] array, int startIndex, int endIndex) {
        double[] slicedArray = new double[endIndex - startIndex];
        System.arraycopy(array, startIndex, slicedArray, 0, endIndex - startIndex);
        return slicedArray;
    }

    @Override
    public void unhandled(Object message) {
        throw new InvalidMessageException("Invalid message received");
    }
}
