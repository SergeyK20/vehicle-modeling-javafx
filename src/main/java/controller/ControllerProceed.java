package main.java.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import main.java.model.Transport;

import java.util.Map;

public class ControllerProceed implements EventHandler<ActionEvent> {

    private Map<String, Transport>[] listListsAutoInRoad;
    private Boolean isPause;

    @SafeVarargs
    public ControllerProceed(Map<String, Transport>... listListsAutoInRoad) {
        this.listListsAutoInRoad = listListsAutoInRoad;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        int i = 0;
        while(i < listListsAutoInRoad.length) {
            if (!listListsAutoInRoad[i].isEmpty()) {
                for (Transport transport : listListsAutoInRoad[i].values()) {
                    System.out.println(transport.getIdNode());
                    if (!transport.isFlagPause()) {
                        transport.getAnimation().play();
                        System.out.println(transport.getIdNode());
                    }
                }
            }
            i++;
        }
    }

    public Boolean getPause() {
        return isPause;
    }

    public void setPause(Boolean pause) {
        isPause = pause;
    }
}
