package main.java.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.controller.ControllerStartScene;
import main.java.model.BuilderRoad;
import main.java.model.Highway;
import main.java.model.Tunnel;

public class StartScene {

    private Stage stage;
    private BuilderRoad road;

    public StartScene(Stage stage) {
        this.stage = stage;
        stage.initStyle(StageStyle.UTILITY);
    }

    public void start() {
        Button menuDirectory = new Button("Справка");
        menuDirectory.setLayoutY(0);
        menuDirectory.setLayoutX(0);
        menuDirectory.setMinSize(100, 25);
        menuDirectory.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText("Информация о программе");
            alert.setContentText(
                    "Лабораторный практикум по дисциплине: \n" +
                            "       \" Технологии программирования \"\n" +
                            "Тема: " +
                            "\"Система моделирования движения транспорта\n" +
                            "                  в тоннеле и на автостраде\"\n" +
                            "\n" +
                            "Разработчики:\n" +
                            "   Студенты группы 6404:\n" +
                            "   Кашкинов Сергей \n" +
                            "   Балаев Рафаил\n" +
                            "Руководитель:\n" +
                            "   Зеленко Лариса Сергеевна\n" +
                            "               Самарский университет 2020");
            alert.showAndWait();
        });


        Label labelHello = new Label("Добро пожаловать!");
        labelHello.setLayoutY(70);
        labelHello.setLayoutX(100);
        labelHello.setFont(new Font("Aria", 30));

        Pane paneStart = new Pane();

        Button simulationSettings = new Button("Ок");
        simulationSettings.setLayoutX(170);
        simulationSettings.setLayoutY(245);
        simulationSettings.setMinSize(115, 25);

        ObservableList<String> listRoad = FXCollections.observableArrayList("Тоннель", "Автодорога");
        ComboBox<String> comboBox = new ComboBox<String>(listRoad);
        comboBox.setLayoutX(170);
        comboBox.setLayoutY(165);
        comboBox.setMinSize(100, 25);

        if (road == null || road instanceof Tunnel) {
            comboBox.setValue("Тоннель");
        } else {
            comboBox.setValue("Автодорога");
        }


        Scene sceneStart = new Scene(paneStart, 450, 300);

        paneStart.getChildren().addAll(simulationSettings, menuDirectory, comboBox, labelHello);


        simulationSettings.setOnMousePressed(event -> {
            if (comboBox.getValue().equals("Тоннель")) {
                road = new Tunnel();
            } else {
                road = new Highway();
            }
            simulationSettings.setOnAction(new ControllerStartScene(stage, road, this));
        });

        stage.setTitle("Моделирование движения автомобилей");


        stage.setX((Screen.getPrimary().getBounds().getMaxX() - 450.0) / 2.0);
        stage.setY((Screen.getPrimary().getBounds().getMaxY() - 300) / 2.0);
        stage.setResizable(false);
        stage.setScene(sceneStart);
        stage.show();

    }
}
