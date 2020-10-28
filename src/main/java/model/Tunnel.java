package main.java.model;

public class Tunnel implements BuilderRoad {

    private Background background;
    private Speed speed;
    private StreamTransport streamTransport;


    @Override
    public void setCountRoadBackground(int n) {
        this.background = new Background(this);
    }

    @Override
    public Background getCountRoadBackground() {
        return background;
    }

    @Override
    public void setSpeed(String nameDistributionLow, int... args) {
        if (nameDistributionLow.equals("norm")) {
            speed = new Speed(this, args[0], args[1]);
        }
        if (nameDistributionLow.equals("uniform")) {
            speed = new Speed(this, args[0], args[1], "uniform");
        }
        if (nameDistributionLow.equals("exponential")) {
            speed = new Speed(this, args[0], "exponential");
        }
        if (nameDistributionLow.equals("discrete")) {
            speed = new Speed(this, args[0]);
        }
    }

    @Override
    public Speed getSpeed() {
        return speed;
    }

    @Override
    public void setStreamTransport(String nameDistributionLow, int... args) {
        if (nameDistributionLow.equals("norm")) {
            streamTransport = new StreamTransport(this, args[0], args[1]);
        }
        if (nameDistributionLow.equals("uniform")) {
            streamTransport = new StreamTransport(this, args[0], args[1], "uniform");
        }
        if (nameDistributionLow.equals("exponential")) {
            streamTransport = new StreamTransport(this, args[0], "exponential");
        }
        if (nameDistributionLow.equals("discrete")) {
            streamTransport = new StreamTransport(this, args[0]);
        }
    }

    @Override
    public StreamTransport getStreamTransport() {
        return streamTransport;
    }
}
