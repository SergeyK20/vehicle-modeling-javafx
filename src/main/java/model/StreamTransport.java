package main.java.model;

public class StreamTransport {


    private double times;
    private String nameDistributionLow;
    private int expectedValue;
    private int dispersion;
    private int startBoundary;
    private int endBoundary;
    private double intensity;

    //для тоннеля
    public StreamTransport(Tunnel tunnel, double times) {
        this.times = times;
        this.nameDistributionLow = Distribution.DETERMINISTIC.getNameDistribution();
    }

    public StreamTransport(Tunnel tunnel, int expectedValue, int dispersion) {
        this.expectedValue = expectedValue;
        this.dispersion = dispersion;
        nameDistributionLow = Distribution.NORM.getNameDistribution();
        //скорость для нормального закона распределения
    }

    public StreamTransport(Tunnel tunnel, int startBoundary, int endBoundary, String nameDistributionLow) {
        this.startBoundary = startBoundary;
        this.endBoundary = endBoundary;
        this.nameDistributionLow = nameDistributionLow;
        //скорость для равномерного закона распределения
    }

    public StreamTransport(Tunnel tunnel, double intensity, String nameDistributionLow) {
        //скорость для экспоненциального закона распределения
        this.intensity = intensity;
        this.nameDistributionLow = nameDistributionLow;
    }

    public StreamTransport(Highway highway, double times) {
        this.times = times;
        this.nameDistributionLow = Distribution.DETERMINISTIC.getNameDistribution();
    }

    public StreamTransport(Highway highway, int expectedValue, int dispersion) {
        //скорость для нормального закона распределения
        this.expectedValue = expectedValue;
        this.dispersion = dispersion;
        nameDistributionLow = Distribution.NORM.getNameDistribution();
    }

    public StreamTransport(Highway highway, int startBoundary, int endBoundary, String nameDistributionLow) {
        //скорость для равномерного закона распределения
        this.startBoundary = startBoundary;
        this.endBoundary = endBoundary;
        this.nameDistributionLow = nameDistributionLow;
    }

    public StreamTransport(Highway highway, double intensity, String nameDistributionLow) {
        //скорость для экспоненциального закона распределения
        this.intensity = intensity;
        this.nameDistributionLow = nameDistributionLow;
    }


    public double getTimes() {
        if (nameDistributionLow.equals(Distribution.NORM.getNameDistribution())) {
            this.times = GenerationValues.generationNorm(Math.sqrt(dispersion), expectedValue);
            return times;
        }
        if (nameDistributionLow.equals(Distribution.UNIFORM.getNameDistribution())) {
            this.times = GenerationValues.generationUniform(startBoundary, endBoundary);
            return times;
        }
        if (nameDistributionLow.equals(Distribution.EXPONENTIAL.getNameDistribution())) {
            this.times = GenerationValues.generationExponential(intensity / 60.0) + 1;
            return times;
        }
        return times;
    }

    public void setTimes(double times) {
        this.times = times;
    }

    public String getNameDistributionLow() {
        return nameDistributionLow;
    }

    public void setNameDistributionLow(String nameDistributionLow) {

        this.nameDistributionLow = nameDistributionLow;
    }

    public int getExpectedValue() {
        return expectedValue;
    }

    public void setExpectedValue(int expectedValue) {
        this.expectedValue = expectedValue;
    }

    public int getDispersion() {
        return dispersion;
    }

    public void setDispersion(int dispersion) {
        this.dispersion = dispersion;
    }

    public int getStartBoundary() {
        return startBoundary;
    }

    public void setStartBoundary(int startBoundary) {
        this.startBoundary = startBoundary;
    }

    public int getEndBoundary() {
        return endBoundary;
    }

    public void setEndBoundary(int endBoundary) {
        this.endBoundary = endBoundary;
    }

    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }
}
