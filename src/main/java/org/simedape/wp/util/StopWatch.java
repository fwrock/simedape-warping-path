package org.simedape.wp.util;

public class StopWatch {

    private long startTime;
    private long stopTime;
    private boolean running;

    public StopWatch() {
        startTime = 0;
        stopTime = 0;
        running = false;
    }

    public void start() {
        startTime = System.currentTimeMillis();
        running = true;
    }

    public void stop() {
        stopTime = System.currentTimeMillis();
        running = false;
    }

    public long getElapsedTime() {
        long elapsed;
        if (running) {
            elapsed = (System.currentTimeMillis() - startTime);
        } else {
            elapsed = (stopTime - startTime);
        }
        return elapsed;
    }

    public long getElapsedTimeSecs() {
        long elapsed;
        if (running) {
            elapsed = ((System.currentTimeMillis() - startTime) / 1000);
        } else {
            elapsed = ((stopTime - startTime) / 1000);
        }
        return elapsed;
    }

    public void reset() {
        startTime = 0;
        stopTime = 0;
        running = false;
    }

    public boolean isRunning() {
        return running;
    }

    public static void main(String[] args) {
        StopWatch watch = new StopWatch();
        watch.start();
        for (int i = 0; i < 1000000; i++) {
            // do nothing
        }
        watch.stop();
        System.out.println("Elapsed time: " + watch.getElapsedTime());
    }

}
