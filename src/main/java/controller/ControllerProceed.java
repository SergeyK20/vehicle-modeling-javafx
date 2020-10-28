package main.java.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import main.java.model.Transport;

import java.util.Map;

public class ControllerProceed implements EventHandler<ActionEvent> {

    private Map<String, Transport> list;
    private Boolean isPause;

    public ControllerProceed(Map<String, Transport> list) {
        this.list = list;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if (!list.isEmpty()) {
            for (Transport transport : list.values()) {
                System.out.println(transport.getIdNode());
                if (!transport.isFlagPause()) {
                    transport.getAnimation().play();
                    System.out.println(transport.getIdNode());
                }
            }

            System.out.println("Продолжить...");
        }
    }

    public Boolean getPause() {
        return isPause;
    }

    public void setPause(Boolean pause) {
        isPause = pause;
    }
}
