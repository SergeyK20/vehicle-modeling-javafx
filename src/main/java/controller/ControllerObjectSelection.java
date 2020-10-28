package main.java.controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import main.java.model.Transport;
import main.java.view.GenericSceneTunnel;

import java.util.concurrent.ConcurrentMap;

public class ControllerObjectSelection implements EventHandler<MouseEvent> {

    private GenericSceneTunnel genericSceneTunnel;

    private ConcurrentMap<String, Transport> list;

    public ControllerObjectSelection(ConcurrentMap<String, Transport> list, GenericSceneTunnel genericSceneTunnel){
        this.list = list;
        this.genericSceneTunnel = genericSceneTunnel;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        for (Transport element : list.values()) {
            if(element != null) {
                if (element.getStroke() != null) {
                    element.setStroke(null);
                }
            }
        }
        Transport transport = (Transport) mouseEvent.getTarget();
        transport.setStroke(Color.MAGENTA);
        transport.setStrokeType(StrokeType.OUTSIDE);
        genericSceneTunnel.initButtonEvent(transport);
    }
}
