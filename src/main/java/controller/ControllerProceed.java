package main.java.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import main.java.model.Transport;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Класс запускающий всю анимацию после паузы
 */
public class ControllerProceed implements EventHandler<ActionEvent> {

    private List<CopyOnWriteArrayList<Transport>> listListsAutoInRoad;
    private Boolean isPause;

    public ControllerProceed(List<CopyOnWriteArrayList<Transport>> listListsAutoInRoad) {
        this.listListsAutoInRoad = listListsAutoInRoad;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        int i = 0;
        while(i < listListsAutoInRoad.size()) {
            if (!listListsAutoInRoad.get(i).isEmpty()) {
                for (Transport transport : listListsAutoInRoad.get(i)) {
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
