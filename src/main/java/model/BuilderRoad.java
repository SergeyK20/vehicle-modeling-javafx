package main.java.model;

/**
 * Интерфейс сборки всех настроек дороги
 */
public interface BuilderRoad {
    void setCountRoadBackground(int n);

    Background getCountRoadBackground();

    void setSpeed(String nameDistributionLow, int... args);

    Speed getSpeed();

    void setStreamTransport(String nameDistributionLow, int... args);

    StreamTransport getStreamTransport();
}
