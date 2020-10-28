package main.java.model;

public interface BuilderRoad {
    void setCountRoadBackground(int n);

    Background getCountRoadBackground();

    void setSpeed(String nameDistributionLow, int... args);

    Speed getSpeed();

    void setStreamTransport(String nameDistributionLow, int... args);

    StreamTransport getStreamTransport();
}
