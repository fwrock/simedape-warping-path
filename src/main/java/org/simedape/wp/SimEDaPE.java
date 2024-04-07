package org.simedape.wp;

import akka.actor.ActorSystem;
import akka.actor.Props;
import org.simedape.wp.actor.CheckSystemActor;
import org.simedape.wp.actor.ClusterActor;
import org.simedape.wp.actor.ReportWriterActor;
import org.simedape.wp.domain.message.CheckQueueMessage;
import org.simedape.wp.domain.message.StartMessage;
import org.simedape.wp.util.Generators;
import org.simedape.wp.util.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimEDaPE {

    private static final Logger logger = LoggerFactory.getLogger(SimEDaPE.class);

    public static void main(String[] args) {
        logger.info("Hello, SimEDaPE!");

        final Integer numberOfClusters = args.length > 1 ? Integer.parseInt(args[0]) : 64;
        final Integer timeSeriesDataPoints = args.length > 2 ? Integer.parseInt(args[1]) : 3403;
        final Integer numberOfTimeSeriesByCluster = args.length > 3 ? Integer.parseInt(args[2]) : 6490;

        final var clustering = Generators.generateClusteringStringObject(numberOfClusters, timeSeriesDataPoints);

        final var watch = new StopWatch();

        watch.start();

        final var system = ActorSystem.create("SimEDaPE");

        final var checkSystemActor =  system.actorOf(Props.create(CheckSystemActor.class, numberOfClusters), "CheckSystemActor");
        final var reportActor = system.actorOf(Props.create(ReportWriterActor.class), "ReportWriterActor");

        reportActor.tell("Start", system.guardian());

        clustering.simulationPoints().forEach(simulationPoint -> {
            final var clusterActor = system.actorOf(Props.create(ClusterActor.class, checkSystemActor, timeSeriesDataPoints, numberOfTimeSeriesByCluster), "ClusterActor-" + simulationPoint.index());
            clusterActor.tell(new StartMessage(simulationPoint.index(), simulationPoint.timeSeries(), "Start"), system.guardian());
        });

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutting down...");
            System.out.println("Shutting down...");
            watch.stop();
            logger.info("Elapsed time={}", watch.getElapsedTime());
            System.out.println("Elapsed time=" + watch.getElapsedTime());
            System.out.println("Elapsed time in seconds=" + watch.getElapsedTimeSecs());
            logger.info("Elapsed time in seconds={}", watch.getElapsedTimeSecs());
        }));

        system.scheduler().schedule(
                java.time.Duration.ofSeconds(1),
                java.time.Duration.ofSeconds(1),
                checkSystemActor,
                new CheckQueueMessage(watch),
                system.dispatcher(),
                null
        );
    }
}
