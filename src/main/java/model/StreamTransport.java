package main.java.model;

public class StreamTransport {


    private int times;
    private String nameDistributionLow;

    //для тоннеля
    public StreamTransport(Tunnel tunnel, int times){
        this.times = times;
        this.nameDistributionLow = "discrete";
    }

    public StreamTransport(Tunnel tunnel, int expectedValue, int dispersion){
        //время генерации потока для нормального закона распределения
    }

    public StreamTransport(Tunnel tunnel, int startBoundary, int endBoundary, String nameDistributionLow){
        //время генерации потока для равномерного закона распределения
    }

    public StreamTransport(Tunnel tunnel, int intensity, String nameDistributionLow){
        //время генерации потока для экспоненциального закона распределения
    }

    //для автострады
    public StreamTransport(Highway highway, int times){
        this.times = times;
    }

    public StreamTransport(Highway highway, int expectedValue, int dispersion){
        //время генерации потока для нормального закона распределения
    }

    public StreamTransport(Highway highway, int startBoundary, int endBoundary, String nameDistributionLow){
        //время генерации потока для равномерного закона распределения
    }

    public StreamTransport(Highway highway, int intensity, String nameDistributionLow){
        //время генерации потока для экспоненциального закона распределения
    }

    public int getTimes() {
        /*if(nameDistributionLow.equals("norm")){
            return times;
        }
        if(nameDistributionLow.equals("uniform")){
            return times;
        }
        if(nameDistributionLow.equals("exponential")){
            return times;
        }*/
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getNameDistributionLow() {
        return nameDistributionLow;
    }

    public void setNameDistributionLow(String nameDistributionLow) {
        this.nameDistributionLow = nameDistributionLow;
    }
}
