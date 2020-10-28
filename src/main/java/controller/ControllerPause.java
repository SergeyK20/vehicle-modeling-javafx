package main.java.controller;

import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import main.java.model.Transport;
import main.java.view.GenericSceneTunnel;

import java.util.Map;

public class ControllerPause implements EventHandler<ActionEvent> {

    private Map<String, Transport>[] listListsAutoInRoad;
    private Boolean newTimes;
    private GenericSceneTunnel genericSceneTunnel;

    @SafeVarargs
    public ControllerPause(GenericSceneTunnel genericSceneTunnel, Map<String, Transport>... listListsAutoInRoad) {
        this.listListsAutoInRoad = listListsAutoInRoad;
        this.genericSceneTunnel = genericSceneTunnel;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        int i = 0;
        genericSceneTunnel.initNewTimeSec();
        while (i < listListsAutoInRoad.length) {
            if (!listListsAutoInRoad[i].isEmpty()) {
                for (Transport transport : listListsAutoInRoad[i].values()) {
                    if (transport.getAnimation().getStatus() == Animation.Status.RUNNING) {
                        transport.getAnimation().pause();
                    }
                }
            }
            i++;
        }
    }

    public Boolean getNewTimes() {
        return newTimes;
    }

    public void setNewTimes(Boolean newTimes) {
        this.newTimes = newTimes;
    }
}
