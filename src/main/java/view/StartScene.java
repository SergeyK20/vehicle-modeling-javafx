package main.java.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.java.controller.ControllerStartScene;
import main.java.model.BuilderRoad;
import main.java.model.Highway;
import main.java.model.Tunnel;

import java.util.ArrayList;
import java.util.List;

public class StartScene {

    private Stage stage;
    private Button simulationSettings;
    private Button referenceInformation;
    private BuilderRoad road;

    public StartScene(Stage stage) {
        this.stage = stage;
    }

    public void start() {
        Pane paneStart = new Pane();
        paneStart.maxHeight(600.0);
        paneStart.maxWidth(800.0);
        simulationSettings = new Button("Настройки моделирования");
        referenceInformation = new Button("Справочная информация");
        simulationSettings.setLayoutX(100);
        simulationSettings.setLayoutY(20);
        referenceInformation.setLayoutX(300);
        referenceInformation.setLayoutY(20);
        ObservableList<String> listRoad = FXCollections.observableArrayList("Тоннель", "Автодорога");
        ComboBox<String> comboBox = new ComboBox<String>(listRoad);
        comboBox.setValue("Тоннель");
        comboBox.setLayoutX(350);
        comboBox.setLayoutY(300);
        simulationSettings.setOnMousePressed(event -> {
            if(comboBox.getValue().equals("Тоннель")){
                road = new Tunnel();
                road.setCountRoadBackground(1);
            } else {
                road = new Highway();
            }
            simulationSettings.setOnAction(new ControllerStartScene(stage, road));
        });
        Scene sceneStart = new Scene(paneStart, 800, 600);
        paneStart.getChildren().addAll(simulationSettings, referenceInformation, comboBox);
        stage.centerOnScreen();
        stage.setScene(sceneStart);
        stage.show();
    }

    public Button getSimulationSettings() {
        return simulationSettings;
    }

    public Button getReferenceInformation() {
        return referenceInformation;
    }

    public Stage getStage() {
        return stage;
    }
}
