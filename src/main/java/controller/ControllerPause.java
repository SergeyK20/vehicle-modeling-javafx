package main.java.controller;

import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import main.java.model.ControlOneRoad;
import main.java.model.Transport;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Класс ставящий на паузу всю анимацию на сцене
 */
public class ControllerPause implements EventHandler<ActionEvent> {

    private List<CopyOnWriteArrayList<Transport>> listListsAutoInRoad;
    private Boolean newTimes;
    private List<ControlOneRoad> controlOneRoadList;


    public ControllerPause(List<ControlOneRoad> controlOneRoadList, List<CopyOnWriteArrayList<Transport>> listListsAutoInRoad) {
        this.listListsAutoInRoad = listListsAutoInRoad;
        this.controlOneRoadList = controlOneRoadList;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        int i = 0;
        while (i < listListsAutoInRoad.size()) {
            controlOneRoadList.get(i).getControllerGenericAuto().initNewTimeSec();
            if (!listListsAutoInRoad.get(i).isEmpty()) {
                for (Transport transport : listListsAutoInRoad.get(i)) {
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
