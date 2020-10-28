package main.java.controller;

import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import main.java.model.Transport;
import main.java.view.GenericScene;

import java.util.Map;

public class ControllerPause implements EventHandler<ActionEvent> {

    private Map<String, Transport> list;
    private Boolean newTimes;
    private GenericScene genericScene;

    public ControllerPause(Map<String, Transport> list, GenericScene genericScene) {
        this.list = list;
        this.genericScene = genericScene;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if (!list.isEmpty()) {
            for (Transport transport : list.values()) {
                if(transport.getAnimation().getStatus() == Animation.Status.RUNNING) {
                    transport.getAnimation().pause();
                }
            }
            genericScene.initNewTimeSec();
        }
    }

    public Boolean getNewTimes() {
        return newTimes;
    }

    public void setNewTimes(Boolean newTimes) {
        this.newTimes = newTimes;
    }
}
