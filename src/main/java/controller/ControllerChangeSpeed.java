package main.java.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import main.java.model.Transport;

public class ControllerChangeSpeed implements EventHandler<ActionEvent> {

    private String speedKmCh;

    private Transport transport;


    public ControllerChangeSpeed(String speedKmCh, Transport transport){
        this.speedKmCh = speedKmCh;
        this.transport = transport;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        System.out.println(speedKmCh + " переданная скорость");
        if(Double.parseDouble(speedKmCh) == 0.0){
            transport.getAnimation().pause();
            transport.setFlagPause(true);
        } else {
            transport.setFlagPause(false);
            transport.getAnimation().play();
            double modelSpeed = Double.parseDouble(speedKmCh) / 2000.0;
            transport.getAnimation().setRate(modelSpeed);
            System.out.println(transport.getAnimation().getRate() + " измененная скорость");
        }
    }

    public String getSpeedKmCh() {
        return speedKmCh;
    }

    public void setSpeedKmCh(String speedKmCh) {
        this.speedKmCh = speedKmCh;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }
}
