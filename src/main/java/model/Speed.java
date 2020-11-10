package main.java.model;

public class Speed {

    private double speed;
    private String nameDistributionLow;
    private int expectedValue;
    private int dispersion;
    private int startBoundary;
    private int endBoundary;
    private double intensity;

    public Speed(Tunnel tunnel, double speed) {
        this.speed = speed;
        this.nameDistributionLow = Distribution.DETERMINISTIC.getNameDistribution();
    }

    public Speed(Tunnel tunnel, int expectedValue, int dispersion) {
        this.expectedValue = expectedValue;
        this.dispersion = dispersion;
        nameDistributionLow = Distribution.NORM.getNameDistribution();
        //скорость для нормального закона распределения
    }

    public Speed(Tunnel tunnel, int startBoundary, int endBoundary, String nameDistributionLow) {
        this.startBoundary = startBoundary;
        this.endBoundary = endBoundary;
        this.nameDistributionLow = nameDistributionLow;
        //скорость для равномерного закона распределения
    }

    public Speed(Tunnel tunnel, double intensity, String nameDistributionLow) {
        //скорость для экспоненциального закона распределения
        this.intensity = intensity;
        this.nameDistributionLow = nameDistributionLow;
    }

    public Speed(Highway highway, double speed) {
        this.speed = speed;
        this.nameDistributionLow = Distribution.DETERMINISTIC.getNameDistribution();
    }

    public Speed(Highway highway, int expectedValue, int dispersion) {
        //скорость для нормального закона распределения
        this.expectedValue = expectedValue;
        this.dispersion = dispersion;
        nameDistributionLow = Distribution.NORM.getNameDistribution();
    }

    public Speed(Highway highway, int startBoundary, int endBoundary, String nameDistributionLow) {
        //скорость для равномерного закона распределения
        this.startBoundary = startBoundary;
        this.endBoundary = endBoundary;
        this.nameDistributionLow = nameDistributionLow;
    }

    public Speed(Highway highway, double intensity, String nameDistributionLow) {
        //скорость для экспоненциального закона распределения
        this.intensity = intensity;
        this.nameDistributionLow = nameDistributionLow;
    }


    public double getSpeed() {
        if (nameDistributionLow.equals(Distribution.NORM.getNameDistribution())) {
            speed = GenerationValues.generationNorm(Math.sqrt(dispersion), expectedValue);
            return speed;
        }
        if (nameDistributionLow.equals(Distribution.UNIFORM.getNameDistribution())) {
            speed = GenerationValues.generationUniform(startBoundary, endBoundary);
            return speed;
        }
        if (nameDistributionLow.equals(Distribution.EXPONENTIAL.getNameDistribution())) {
            speed = Math.round(GenerationValues.generationExponential(1/ (intensity / 60.0))) + 20;
            return speed;
        }
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
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
