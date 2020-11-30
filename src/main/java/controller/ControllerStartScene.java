package main.java.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import main.java.model.BuilderRoad;
import main.java.view.SettingsScene;
import main.java.view.StartScene;


public class ControllerStartScene implements EventHandler<ActionEvent> {

    private SettingsScene settingsScene;

    public ControllerStartScene(Stage stage, BuilderRoad road, StartScene startScene){
        this.settingsScene = new SettingsScene(stage, road, startScene);
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        settingsScene.start();
    }
}
