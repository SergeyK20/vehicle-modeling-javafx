package main.java.model;

/**
 * Класс для настройки режима "Автострада" (Настройка фона, генерации скорости и времени)
 */
public class Highway implements BuilderRoad {

    private Background background;
    private Speed speed;
    private StreamTransport streamTransport;

    @Override
    public void setCountRoadBackground(int n) {
        background = new Background(this, n);
    }

    @Override
    public Background getCountRoadBackground() {
        return background;
    }

    @Override
    public void setSpeed(String nameDistributionLow, int... args) {
        if (nameDistributionLow.equals(Distribution.NORM.getNameDistribution())) {
            speed = new Speed(this, args[0], args[1]);
        }
        if (nameDistributionLow.equals(Distribution.UNIFORM.getNameDistribution())) {
            speed = new Speed(this, args[0], args[1], Distribution.UNIFORM.getNameDistribution());
        }
        if (nameDistributionLow.equals(Distribution.EXPONENTIAL.getNameDistribution())) {
            speed = new Speed(this, args[0], Distribution.EXPONENTIAL.getNameDistribution());
        }
        if (nameDistributionLow.equals(Distribution.DETERMINISTIC.getNameDistribution())) {
            speed = new Speed(this, args[0]);
        }
    }

    @Override
    public Speed getSpeed() {
        return speed;
    }

    @Override
    public void setStreamTransport(String nameDistributionLow, int... args) {
        if (nameDistributionLow.equals(Distribution.NORM.getNameDistribution())) {
            streamTransport = new StreamTransport(this, args[0], args[1]);
        }
        if (nameDistributionLow.equals(Distribution.UNIFORM.getNameDistribution())) {
            streamTransport = new StreamTransport(this, args[0], args[1], Distribution.UNIFORM.getNameDistribution());
        }
        if (nameDistributionLow.equals(Distribution.EXPONENTIAL.getNameDistribution())) {
            streamTransport = new StreamTransport(this, args[0], Distribution.EXPONENTIAL.getNameDistribution());
        }
        if (nameDistributionLow.equals(Distribution.DETERMINISTIC.getNameDistribution())) {
            streamTransport = new StreamTransport(this, args[0]);
        }
    }

    @Override
    public StreamTransport getStreamTransport() {
        return streamTransport;
    }
}
