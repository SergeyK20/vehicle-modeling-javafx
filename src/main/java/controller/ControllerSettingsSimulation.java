package main.java.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import main.java.model.BuilderRoad;
import main.java.view.GenericSceneTunnel;

public class ControllerSettingsSimulation implements EventHandler<ActionEvent> {

    private GenericSceneTunnel genericSceneTunnel;

    public ControllerSettingsSimulation(Stage stage, BuilderRoad road){
        this.genericSceneTunnel = new GenericSceneTunnel(stage, road);
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        genericSceneTunnel.start();
    }
}
