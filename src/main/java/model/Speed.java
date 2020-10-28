package main.java.model;

public class Speed {

    private int speed;
    private String nameDistributionLow;

    public Speed(Tunnel tunnel, int speed){
        this.speed = speed;
        this.nameDistributionLow = "discrete";
    }

    public Speed(Tunnel tunnel, int expectedValue, int dispersion){
        //скорость для нормального закона распределения
    }

    public Speed(Tunnel tunnel, int startBoundary, int endBoundary, String nameDistributionLow){
        //скорость для равномерного закона распределения
    }

    public Speed(Tunnel tunnel, int intensity, String nameDistributionLow){
        //скорость для экспоненциального закона распределения
    }

    public Speed(Highway highway, int speed){
        this.speed = speed;
    }

    public Speed(Highway highway, int expectedValue, int dispersion){
        //скорость для нормального закона распределения
    }

    public Speed(Highway highway, int startBoundary, int endBoundary, String nameDistributionLow){
        //скорость для равномерного закона распределения
    }

    public Speed(Highway highway, int intensity, String nameDistributionLow){
        //скорость для экспоненциального закона распределения
    }

    public int getSpeed() {
        if(nameDistributionLow.equals("norm")){
            return speed;
        }
        if(nameDistributionLow.equals("uniform")){
            return speed;
        }
        if(nameDistributionLow.equals("exponential")){
            return speed;
        }
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


}
