package main.java.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import main.java.model.BuilderRoad;
import main.java.view.SettingsScene;


public class ControllerStartScene implements EventHandler<ActionEvent> {

    private SettingsScene settingsScene;
    private BuilderRoad road;

    public ControllerStartScene(Stage stage, BuilderRoad road){
        this.settingsScene = new SettingsScene(stage, road);
        this.road = road;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        settingsScene.start();
    }
}
