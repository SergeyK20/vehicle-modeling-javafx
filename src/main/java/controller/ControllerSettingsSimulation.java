package main.java.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import main.java.model.BuilderRoad;
import main.java.view.GenericScene;

public class ControllerSettingsSimulation implements EventHandler<ActionEvent> {

    private GenericScene genericScene;

    public ControllerSettingsSimulation(Stage stage, BuilderRoad road){
        this.genericScene = new GenericScene(stage, road);
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        genericScene.start();
    }
}
