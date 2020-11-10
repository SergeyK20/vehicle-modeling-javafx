package main.java.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
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
    private BuilderRoad road;

    public StartScene(Stage stage) {
        this.stage = stage;
    }

    public void start() {
        Button menuDirectory = new Button("Справочник");
        menuDirectory.setLayoutY(0);
        menuDirectory.setLayoutX(0);
        menuDirectory.setMinSize(100, 25);
        menuDirectory.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText("Информация о программе");
            alert.setContentText("Программа моделирующая движение автомобилей." +
                    "Доступно два режима дороги на каждой дороге можно выбирать" +
                    "способ генерирования автомобилей и их скоростей." +
                    "Работу сделали: \n" +
                    "Кашкинов Сергей \n" +
                    "Балаев Рафаил");
            alert.showAndWait();
        });

        Button close = new Button("Выход");
        close.setMinSize(100, 25);
        close.setLayoutX(700);
        close.setLayoutY(550);
        close.setOnAction(actionEvent -> {
            System.exit(0);
        });

        Label labelHello = new Label("Добро пожаловать!");
        labelHello.setLayoutY(200);
        labelHello.setLayoutX(270);
        labelHello.setFont(new Font("Aria", 30));

        Pane paneStart = new Pane();

        simulationSettings = new Button("Ок");
        simulationSettings.setLayoutX(350);
        simulationSettings.setLayoutY(450);
        simulationSettings.setMinSize(115, 25);

        ObservableList<String> listRoad = FXCollections.observableArrayList("Тоннель", "Автодорога");
        ComboBox<String> comboBox = new ComboBox<String>(listRoad);
        comboBox.setLayoutX(350);
        comboBox.setLayoutY(350);
        comboBox.setMinSize(100, 25);

        if (road == null || road instanceof Tunnel) {
            comboBox.setValue("Тоннель");
        } else {
            comboBox.setValue("Автодорога");
        }


        Scene sceneStart = new Scene(paneStart, 800, 600);
        paneStart.getChildren().addAll(simulationSettings, menuDirectory, comboBox, close, labelHello);


        simulationSettings.setOnMousePressed(event -> {
            if (comboBox.getValue().equals("Тоннель")) {
                road = new Tunnel();
            } else {
                road = new Highway();
            }
            simulationSettings.setOnAction(new ControllerStartScene(stage, road, this));
        });

        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setScene(sceneStart);
        stage.show();

    }
}
